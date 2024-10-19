document.addEventListener("DOMContentLoaded", () => {
    const usuario = sessionStorage.getItem('usuario');
    const token = sessionStorage.getItem('token');

    const header = document.querySelector('header');
    const nav = header.querySelector('nav'); // Selecciona el elemento nav
    const loginLinks = header.querySelectorAll('ul:nth-child(2) li'); // Selecciona los enlaces de login/registro

    if (token) {
        nav.innerHTML = ''; //limpia todo el nav para poder crearlo devuelta

        // Crea los elementos de navegación
        const logoDiv = document.createElement('div');
        logoDiv.className = 'text-3xl font-bold flex items-center';
        logoDiv.innerHTML = `
            <img src="assets/logo solo.png" alt="Logo Clave de Sol" class="mr-2 object-contain" style="width: 90px; height: 90px;">
            <h3><a href="index.html">Clave de Sol</a></h3>
        `;
        
        const navLinks = document.createElement('ul');
        navLinks.className = 'flex space-x-4';
        navLinks.innerHTML = `
            <li><a href="index.html" class="hover:text-custom-orange">Inicio</a></li>
            <li><a href="sobre-nosotros.html" class="hover:text-custom-orange">Sobre Nosotros</a></li>
            <li><a href="productos.html" class="hover:text-custom-orange">Productos</a></li>
            <li><a href="contacto.html" class="hover:text-custom-orange">Contacto</a></li>
        `;

        const seccionUsuario = document.createElement('ul'); // Cambiar el ul para mantener el estilo
        seccionUsuario.className = 'flex items-center space-x-4';
        seccionUsuario.innerHTML = `
            <li class="font-bold">${usuario}</li> <!-- Usar 'usuario' para mostrar el nombre -->
            <li><a href="carrito.html" class="hover:text-custom-orange">Carrito</a></li>
            <li><a href="./index.html" class="hover:text-custom-orange" onclick="logout()">Cerrar Sesión</a></li>
        `;

        // Añade todos los elementos a la navegación
        nav.appendChild(logoDiv);
        nav.appendChild(navLinks);
        nav.appendChild(seccionUsuario);

    }
});

// Función para cerrar sesión
function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('usuario');
    window.location.href = "./index.html"; 
}