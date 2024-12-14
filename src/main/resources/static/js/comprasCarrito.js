
// Función para actualizar la cantidad de un producto
function actualizarCantidad(productoID, cantidad) {
    const usernameElement = document.getElementById('usernameLogueado');
    const username = usernameElement ? usernameElement.textContent.trim() : 'defaultUser'; // Obtener el username desde el HTML

    let carrito = JSON.parse(localStorage.getItem("carrito_" + username));

    if (carrito) {
        let item = carrito.find(p => p.productoID === productoID);

        if (item) {
            cantidad = parseInt(cantidad);

            // Validación: cantidad no puede ser menor a 1
            if (cantidad < 1) {
                alert("La cantidad no puede ser menor a 1.");
                return;
            }

            // Validación: cantidad no puede superar stock ni el límite de 15
            const cantidadMaximaSegunStock = Math.min(item.productoStock, 15);
            if (cantidad > cantidadMaximaSegunStock) {
                alert(`Cantidad inválida. No puedes superar el stock disponible (${item.productoStock}) ni el límite de 15.`);
                return;
            }

            // Actualizar la cantidad y guardar en localStorage
            item.cantidad = cantidad;
            localStorage.setItem("carrito_" + username, JSON.stringify(carrito));

            // Actualizar subtotal en la vista
            const subtotal = item.productoPrecio * item.cantidad;
            document.getElementById(`subtotal-${productoID}`).textContent = `S/ ${subtotal.toFixed(2)}`;

            // Actualizar el total global
            actualizarTotalCarrito();
        }
    }
}


// Función para regrear al Home
function home(){
	window.location.href = '/user/index'; // Redirige a la URL indicada
}

// Función para actualizar el total global del carrito
function actualizarTotalCarrito() {
    const usernameElement = document.getElementById('usernameLogueado');
    const username = usernameElement ? usernameElement.textContent.trim() : 'defaultUser';

    const carrito = JSON.parse(localStorage.getItem("carrito_" + username));

    if (carrito) {
        const totalCarrito = carrito.reduce((total, item) => total + item.productoPrecio * item.cantidad, 0);
        document.getElementById("totalCarrito").textContent = "S/ " + totalCarrito.toFixed(2);
    }
}

// Función para eliminar un producto del carrito
function eliminarProducto(productoID) {
    const usernameElement = document.getElementById('usernameLogueado');
    const username = usernameElement ? usernameElement.textContent.trim() : 'defaultUser';

    let carrito = JSON.parse(localStorage.getItem("carrito_" + username));

    if (carrito) {
        carrito = carrito.filter(p => p.productoID !== productoID);

        localStorage.setItem("carrito_" + username, JSON.stringify(carrito));

        // Eliminar el producto del DOM
        document.getElementById(`producto-${productoID}`).remove();

        // Actualizar el total global
        actualizarTotalCarrito();
    }
}

// Cargar los productos del carrito al iniciar
window.onload = function () {
    const usernameElement = document.getElementById('usernameLogueado');
    const username = usernameElement ? usernameElement.textContent.trim() : 'defaultUser';

    const carrito = JSON.parse(localStorage.getItem("carrito_" + username));
    const carritoItemsContainer = document.getElementById("carritoItems");

    if (carrito && carrito.length > 0) {
        carrito.forEach(item => {
            const subtotal = item.productoPrecio * item.cantidad;

            const row = document.createElement("div");
            row.classList.add("row", "carrito-item");
            row.id = `producto-${item.productoID}`;

            row.innerHTML = `
                <div class="col-md-3"><span>${item.productoNombre}</span></div>
                <div class="col-md-2">${item.productoMarca}</div>
                <div class="col-md-2">S/ ${item.productoPrecio}</div>
                <div class="col-md-2" id="subtotal-${item.productoID}">S/ ${subtotal.toFixed(2)}</div>
                <div class="col-md-2">
                    <input type="number" class="cantidad-input" value="${item.cantidad}" min="1" max="${Math.min(item.productoStock, 15)}"
                        onchange="actualizarCantidad('${item.productoID}', this.value)">
                </div>
                <div class="col-md-1">
                    <button class="btn btn-danger btn-sm" onclick="eliminarProducto('${item.productoID}')">X</button>
                </div>
            `;

            carritoItemsContainer.appendChild(row);
        });

        actualizarTotalCarrito();
    } else {
        carritoItemsContainer.innerHTML = "<p>No hay productos en el carrito.</p>";
    }
};


function realizarPago(){
	const usernameElement = document.getElementById('usernameLogueado');
	const username = usernameElement ? usernameElement.textContent.trim() : 'visitante';

	// Actualizar el carrito del usuario a un arreglo vacío
	localStorage.setItem("carrito_" + username, JSON.stringify([]));

	console.log(`El carrito de ${username} ha sido vaciado.`);
	
	home();
}