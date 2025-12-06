document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const mensaje = document.getElementById('mensaje');
    
    // Mostrar loading
    const btnLogin = document.querySelector('.btn-login');
    const textoOriginal = btnLogin.textContent;
    btnLogin.textContent = 'Ingresando...';
    btnLogin.disabled = true;
    
    // Limpiar mensaje previo
    mensaje.style.display = 'none';
    
    // Enviar datos al servlet con URL COMPLETA
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    
    console.log('🔍 Enviando login a: http://localhost:8080/proyectoservlets/login');
    console.log('👤 Usuario:', username);
    
    fetch('http://localhost:8080/proyectoservlets/login', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        console.log('📡 Respuesta recibida - Status:', response.status);
        
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor: ' + response.status);
        }
        
        return response.json();
    })
    .then(data => {
        console.log('✅ Datos recibidos:', data);
        
        if (data.success) {
            // Login exitoso
            mensaje.className = 'mensaje success';
            mensaje.textContent = data.mensaje;
            mensaje.style.display = 'block';
            
            // Redirigir al dashboard
            setTimeout(() => {
                window.location.href = data.redirect;
            }, 1000);
            
        } else {
            // Login fallido
            mensaje.className = 'mensaje error';
            mensaje.textContent = data.mensaje;
            mensaje.style.display = 'block';
            
            // Restaurar botón
            btnLogin.textContent = textoOriginal;
            btnLogin.disabled = false;
            
            // Limpiar contraseña
            document.getElementById('password').value = '';
        }
    })
    .catch(error => {
        console.error('❌ Error completo:', error);
        mensaje.className = 'mensaje error';
        mensaje.textContent = 'Error: ' + error.message;
        mensaje.style.display = 'block';
        
        btnLogin.textContent = textoOriginal;
        btnLogin.disabled = false;
    });
});