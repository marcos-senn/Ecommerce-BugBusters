
const apiURL = 'http://localhost:8080/productos'; 

const productosContainer = document.getElementById('productos-container');

const crearProductos = (productos) => {
    productosContainer.innerHTML = ''; 

    productos.forEach(producto => {
        const productoDiv = document.createElement('div');
        productoDiv.classList.add('producto');

        const img = document.createElement('img');
        img.src = `http://localhost:8080${producto.imagenUrl}`
        img.alt = producto.nombre;

        const nombre = document.createElement('h2');
        nombre.textContent = producto.nombre;
        nombre.classList.add('producto-titulo');

        const precio = document.createElement('p');
        precio.textContent = `USD $${producto.precio}`;
        precio.classList.add('producto-precio');


        productoDiv.appendChild(img);
        productoDiv.appendChild(nombre);
        productoDiv.appendChild(precio);

        productosContainer.appendChild(productoDiv);
    });
}

const obtenerProductos = async (url) => {
    try {
        const response = await fetch(url);
        const productos = await response.json();
        
        crearProductos(productos)
        
    } catch (error) {
        console.error('Error al obtener los productos:', error);
    }
};

obtenerProductos(apiURL);

const obtenerProductosPorCategoria = async (categoria) => {
    const urlFiltro = `${apiURL}/${categoria}`
    obtenerProductos(urlFiltro);
}

document.querySelectorAll('.categoria').forEach(a => {
    a.addEventListener('click', () => {
        const categoria = a.getAttribute('data-categoria');

        obtenerProductosPorCategoria(categoria);
    });
});
