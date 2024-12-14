


function redirigirAlCarrito() {
	window.location.href = '/user/index/carrito'; // Redirige a la URL indicada
}


// Variable para almacenar el producto seleccionado
let productoSeleccionado = {};



// Función que muestra el popup y asigna los valores del producto
function mostrarPopupDeAgregarAlCarrito(button) {
	// Obtener los valores del producto desde los atributos data-* del botón
	productoSeleccionado.productoID = button.getAttribute('data-id');
	productoSeleccionado.productoNombre = button.getAttribute('data-nombre');
	productoSeleccionado.productoPrecio = parseFloat(button.getAttribute('data-precio'));
	productoSeleccionado.productoStock = parseInt(button.getAttribute('data-stock'));
	productoSeleccionado.productoMarca = button.getAttribute('data-marca');

	// Calcular el máximo de stock permitido (mínimo entre el stock disponible y 15)
	const maxStock = Math.min(productoSeleccionado.productoStock, 15);

	// Establecer el campo de cantidad en 1 como valor inicial antes de mostrar el popup
	const cantidadInput = document.getElementById('cantidadInput');
	cantidadInput.value = 1; // Aquí se puede agregar desde 1 a `maxStock`

	// Asegurar que el input tenga límites de cantidad válidos
	cantidadInput.setAttribute('min', 1);
	cantidadInput.setAttribute('max', maxStock);

	// Mostrar el popup para seleccionar la cantidad
	document.getElementById('cantidadPopup').style.display = 'block';
}





// Función para mostrar el carrito con las columnas correctas
function mostrarCarrito() {
	const username = localStorage.getItem('username');
	if (!username) {
		alert("¡Debes iniciar sesión para ver el carrito!");
		return;
	}

	const carritoKey = `carrito_${username}`;
	const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];
	const carritoResumen = document.getElementById('carritoResumen');
	const totalPrecio = document.getElementById('totalPrecio');

	carritoResumen.innerHTML = '';
	let total = 0;

	// Crear filas en la tabla para cada producto en el carrito
	carrito.forEach((item, index) => {
		const precioUnitario = item.productoPrecio;
		const subTotal = precioUnitario * item.cantidad;
		total += subTotal;

		// Crear una fila con las columnas especificadas
		const row = document.createElement('tr');

		row.innerHTML = `
            <td>${item.productoNombre}</td>
            <td>${item.productoMarca}</td> <!-- Mostrar la marca aquí -->
            <td>
                <input type="number" min="1" max="${Math.min(item.productoStock, 15)}" value="${item.cantidad}" 
                       onchange="actualizarCantidad(${index}, this.value)">
            </td>
            <td>S/ ${item.productoPrecio.toFixed(2)}</td>
            <td>S/ ${subTotal.toFixed(2)}</td>
            <td>
                <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${index})">X</button>
            </td>
        `;

		carritoResumen.appendChild(row);
	});

	// Mostrar el total general en la parte inferior
	totalPrecio.textContent = total.toFixed(2);
}





// Función para actualizar la cantidad de un producto en el carrito
function actualizarCantidad(index, nuevaCantidad) {
	const username = localStorage.getItem('username');
	const carritoKey = `carrito_${username}`;
	const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];

	// Convertir la cantidad a número entero
	nuevaCantidad = parseInt(nuevaCantidad);

	// Obtener el producto del carrito
	const producto = carrito[index];

	// Verificar que la nueva cantidad no sea menor a 1
	if (nuevaCantidad < 1) {
		alert("La cantidad no puede ser menor a 1.");
		mostrarCarrito(); // Refrescar la vista del carrito para corregir el valor visualmente
		return;
	}

	// Verificar que la cantidad no supere el stock disponible ni el límite de 15
	const cantidadMaximaSegunStock = Math.min(producto.productoStock, 15); // El máximo permitido es el stock o 15
	if (nuevaCantidad > cantidadMaximaSegunStock) {
		alert(`Cantidad inválida. No puedes superar el stock disponible (${producto.productoStock}) ni el límite de 15.`);
		mostrarCarrito(); // Refrescar la vista del carrito para corregir el valor visualmente
		return;
	}

	// Asegurarse de que la cantidad no supere 15
	if (nuevaCantidad > 15) {
		nuevaCantidad = 15; // Limitar a 15
	}

	// Actualizar la cantidad del producto en el carrito
	producto.cantidad = nuevaCantidad;

	// Guardar el carrito actualizado en localStorage
	localStorage.setItem(carritoKey, JSON.stringify(carrito));

	// Refrescar el carrito en la interfaz para mostrar el subtotal actualizado
	mostrarCarrito();
	actualizarCarrito(username);

	// Asegurar que el campo de cantidad no supere 15
	document.getElementById('cantidadInput').value = Math.min(nuevaCantidad, 15); // Limitar visualmente en el campo
}





// Función para eliminar un producto del carrito
function eliminarProducto(index) {

	ventanaDeAtras = document.getElementById("carritoModal");

	ventanaDeAtras.style.display = 'none';


	// Usamos SweetAlert2 para la confirmación
	Swal.fire({
		title: '¿Estás seguro?',
		text: "Este producto será eliminado del carrito.",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Sí, eliminar',
		cancelButtonText: 'Cancelar',
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) {
			const username = localStorage.getItem('username');
			const carritoKey = `carrito_${username}`;
			const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];

			carrito.splice(index, 1); // Eliminar el producto
			localStorage.setItem(carritoKey, JSON.stringify(carrito));
			mostrarCarrito(); // Refrescar la vista del carrito
			actualizarCarrito(); // Actualizar la cantidad total de productos en el icono del carrito

		}
	});

	ventanaDeAtras.style.display = 'block';
}



// Función para cerrar el modal
document.getElementById('closeCarritoModal').addEventListener('click', function() {
	document.getElementById('carritoModal').style.display = 'none';
});



// Cerrar el popup
function cerrarPopup() {
	document.getElementById('cantidadPopup').style.display = 'none';
}



// Función para agregar productos al carrito
function agregarAlCarritoConCantidad() {

	const cantidad = parseInt(document.getElementById('cantidadInput').value);

	// Verificar que la cantidad sea válida
	if (isNaN(cantidad) || cantidad < 1 || cantidad > productoSeleccionado.productoStock || cantidad > 15) {

		// Ocultar el carrito antes de mostrar el SweetAlert
		const carritoModal = document.getElementById('cantidadPopup');
		if (carritoModal) {
			carritoModal.style.display = 'none';
		}

		Swal.fire({
			icon: 'error',
			title: 'Cantidad inválida',
			text: 'Por favor, ingresa una cantidad válida (1-15).',
		});
		return;
	}

	// Obtener el nombre de usuario de localStorage
	let username = localStorage.getItem('username') || 'visitante';

	// Si es un visitante, mostramos la alerta pero no lo redirigimos
	if (username === 'visitante') {

		// Ocultar el carrito antes de mostrar el SweetAlert
		const carritoModal = document.getElementById('carritoModal');


		if (carritoModal) {
			carritoModal.style.display = 'none';
		}

		Swal.fire({
			icon: 'warning',
			title: 'Debes iniciar sesión',
			text: 'Debes iniciar sesión para agregar productos al carrito.',
		});
		return; // No forzamos redirección, solo mostramos la alerta
		
	} else {
		// Si el usuario está logueado, guardamos el carrito
		const carritoKey = `carrito_${username}`;
		const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];

		// Validar límite de 15 productos diferentes
		if (!carrito.find(item => item.productoID === productoSeleccionado.productoID) && carrito.length >= 15) {
			// Ocultar el carrito antes de mostrar el SweetAlert
			const carritoModal = document.getElementById('cantidadPopup');

			if (carritoModal) {
				carritoModal.style.display = 'none';
			}

			Swal.fire({
				icon: 'error',
				title: 'Carrito lleno',
				text: '¡Tu carrito está lleno!',
			});
			return;
		}

		// Verificar si el producto ya existe en el carrito
		let productoExistente = carrito.find(item => item.productoID === productoSeleccionado.productoID);

		if (productoExistente) {

			const cantidadTotal = productoExistente.cantidad + cantidad;

			// Verificar que no se supere el límite de 15 unidades
			if (cantidadTotal > 15) {
				// Ocultar el carrito antes de mostrar el SweetAlert
				const carritoModal = document.getElementById('cantidadPopup');
				if (carritoModal) {
					carritoModal.style.display = 'none';
				}

				Swal.fire({
					icon: 'error',
					title: 'Límite alcanzado',
					text: 'Ya tienes 15 unidades de este producto en tu carrito.',
				});
				return;
			}

			// Si el producto ya está en el carrito, aumentar la cantidad
			if (productoExistente.cantidad + cantidad <= productoExistente.productoStock) {
				productoExistente.cantidad += cantidad;
				console.log(`Producto actualizado en carrito: ${JSON.stringify(productoExistente)}`);
			} else {
				// Ocultar el carrito antes de mostrar el SweetAlert
				const carritoModal = document.getElementById('cantidadPopup');
				if (carritoModal) {
					carritoModal.style.display = 'none';
				}

				Swal.fire({
					icon: 'error',
					title: 'Sin stock',
					text: 'No hay suficiente stock para este producto.',
				});
				return;
			}
		} else {
			// El producto no existe en el carrito
			productoNombre = productoSeleccionado.productoNombre;

			// Si el producto no está en el carrito, agregarlo
			carrito.push({
				productoID: productoSeleccionado.productoID,
				productoPrecio: productoSeleccionado.productoPrecio,
				cantidad: cantidad,
				productoStock: productoSeleccionado.productoStock,
				productoNombre: productoSeleccionado.productoNombre,
				productoMarca: productoSeleccionado.productoMarca // Aquí agregamos la marca
			});
			// console.log(`Producto agregado al carrito: ${JSON.stringify(carrito[carrito.length - 1])}`);
		}

		// Guardar los cambios en el carrito en localStorage
		localStorage.setItem(carritoKey, JSON.stringify(carrito));
		// console.log(`Carrito guardado en localStorage: ${JSON.stringify(carrito)}`);


		// Mostrar mensaje de éxito
		Swal.fire({
			icon: 'success',
			title: 'Producto agregado',
			html: 'Agregado correctamente al carrito.',
		});

		// Actualizar la cantidad de productos en el carrito
		actualizarCarrito(username);
		cerrarPopup(); // Cerrar el popup después de agregar al carrito

	}

}


// Función para actualizar el ícono del carrito con la cantidad de productos
function actualizarCarrito(username) {
    // Verificar si el usuario es 'visitante'
    if (username === 'visitante') {
        const carritoDiv = document.getElementById('productosEnCarritoID');
        if (carritoDiv) {
            carritoDiv.textContent = 0; // Mostrar 0 productos
        } else {
            console.warn("Elemento productosEnCarritoID no encontrado");
        }
        return; // Salir de la función
    }

    // Continuar la lógica si el usuario no es 'visitante'
    const carritoKey = `carrito_${username}`;
    const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];

    // Contar el total de productos en el carrito
    const totalProductos = carrito.reduce((total, item) => total + item.cantidad, 0);

    // Actualizar el contenido del div
    const carritoDiv = document.getElementById('productosEnCarritoID');
    if (carritoDiv) {
        carritoDiv.textContent = totalProductos;
    } else {
        console.warn("Elemento productosEnCarritoID no encontrado");
    }
}


// Mostrar el carrito al hacer clic
document.getElementById('carritoLink').addEventListener('click', function() {
	// Obtener el valor del 'username' desde el localStorage o el DOM (según cómo lo estés almacenando)
	const username = localStorage.getItem('username') || 'visitante'; // Default a 'visitante' si no hay valor en el localStorage

	// Si el usuario es un visitante, no mostrar el modal y mostrar una alerta
	if (username === 'visitante') {
		alert('Debes iniciar sesión para agregar productos al carrito.');
		return; // No mostrar el modal
	}

	// Si no es visitante, mostrar el modal del carrito
	const carritoModal = document.getElementById('carritoModal');
	carritoModal.style.display = 'block';
	mostrarCarrito();
});


// Cerrar el carrito modal
document.getElementById('closeCarritoModal').addEventListener('click', function() {
	document.getElementById('carritoModal').style.display = 'none';
});



// Función para cargar los productos en el carrito
function cargarProductosEnCarrito() {
    const username = localStorage.getItem('username');

    // Verificar si el usuario es visitante
    if (!username || username === 'visitante') {
        const carritoTableBody = document.getElementById('carritoResumen');
        const totalPrecio = document.getElementById('totalPrecio');

        // Mostrar un carrito vacío con total en 0
        if (carritoTableBody) carritoTableBody.innerHTML = ''; // Limpia cualquier contenido
        if (totalPrecio) totalPrecio.textContent = '0.00'; // Establece el total a 0

        return; // Salir de la función
    }

    // Continuar la lógica normal si el usuario no es visitante
    const carritoKey = `carrito_${username}`;
    const carrito = JSON.parse(localStorage.getItem(carritoKey)) || [];
    const carritoTableBody = document.getElementById('carritoResumen');

    carritoTableBody.innerHTML = ''; // Limpiar contenido previo

    carrito.forEach(item => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${item.productoNombre}</td>
            <td>${item.cantidad}</td>
            <td>S/ ${item.productoPrecio * item.cantidad}</td>
        `;
        carritoTableBody.appendChild(tr);
    });

    const total = carrito.reduce((sum, item) => sum + (item.productoPrecio * item.cantidad), 0);
    document.getElementById('totalPrecio').textContent = total.toFixed(2);
}



function cerrarSesionYborrarLS() {
	// Eliminar 'username' del localStorage
	localStorage.removeItem('username');

	// Redirigir al back-end para cerrar la sesión en el servidor
	window.location.href = '/user/logout'; // Redirigir a la ruta que gestiona el logout
}



window.onload = function() {
	const usernameElement = document.getElementById('username');
	let username = localStorage.getItem('username') || 'visitante'; // Si no hay username, es "visitante"

	if (usernameElement) {
		const usernameFromElement = usernameElement.textContent.trim();
		if (usernameFromElement) {
			username = usernameFromElement;
			localStorage.setItem('username', username); // Guardar el nombre de usuario en localStorage si no estaba ya.
		}
	}

	// Verificar si el usuario es "visitante"
	if (username === 'visitante') {
		console.log("Usuario visitante detectado.");
		// Se permite la navegación como visitante con carrito bloqueado.
	} else {
		console.log(`Usuario logueado: ${username}`);
	}

	// Si el usuario no es visitante, se inicializa el carrito para usuarios registrados
	if (username !== 'visitante') {
		const carritoKey = `carrito_${username}`;
		if (!localStorage.getItem(carritoKey)) {
			localStorage.setItem(carritoKey, JSON.stringify([])); // Crear un carrito vacío si no existe
		}
		actualizarCarrito(username); // Llamar a la función para actualizar el carrito visualmente

		// Ocultar el mensaje de éxito después de 2 segundos
		var successMessage = document.getElementById('successMessage');
		if (successMessage) {
			setTimeout(function() {
				successMessage.style.display = 'none';
				console.log('Se cerró el mensaje success.');
			}, 2000); // 2000 ms = 2 segundos
		}

		// Ocultar el mensaje de error después de 2 segundos
		var errorMessage = document.getElementById('errorMessage');
		if (errorMessage) {
			setTimeout(function() {
				errorMessage.style.display = 'none';
			}, 2000); // 2000 ms = 2 segundos
		}

	}
};




document.addEventListener('DOMContentLoaded', function() {

	// Selecciona los botones por su ID
	const contactoBtn = document.getElementById('contactoBtn');
	const nosotrosBtn = document.getElementById('nosotrosBtn');
	const politicasBtn = document.getElementById('politicasBtn');
	const trabajaBtn = document.getElementById('trabajaBtn');
	const terminosBtn = document.getElementById('terminosBtn');
	const problemasBtn = document.getElementById('reportarProblema');

	// Evento de clic para "Políticas de Privacidad"
	politicasBtn.addEventListener('click', function(event) {
		event.preventDefault();
		Swal.fire({
			title: 'Políticas de Privacidad',
			html: `
	               <p>Nuestra política de privacidad asegura que tu información personal está protegida.</p>
	           `,
			icon: 'info',
			confirmButtonText: 'Entendido'
		});
	});

	// Evento de clic para "Trabaja con Nosotros"
	trabajaBtn.addEventListener('click', function(event) {
		event.preventDefault();
		Swal.fire({
			title: 'Trabaja con Nosotros',
			html: `
	               <p>¿Te interesa formar parte de nuestro equipo?</p>
	               <p>Envía tu CV a <strong>p.a.l.store.atencion@gmail.com</strong> para evaluarte!</p>
	           `,
			icon: 'success',
			confirmButtonText: 'Cerrar'
		});
	});

	// Evento de clic para "Términos y Condiciones"
	terminosBtn.addEventListener('click', function(event) {
		event.preventDefault();
		Swal.fire({
			title: 'Términos y Condiciones',
			html: `
	               <p>Al usar nuestro sitio, aceptas los términos y condiciones establecidos.</p>
	           `,
			icon: 'warning',
			confirmButtonText: 'Aceptar'
		});
	});



	// Agrega el evento de clic para "Contacto"
	contactoBtn.addEventListener('click', function(event) {
		event.preventDefault(); // Evita la acción predeterminada del enlace
		Swal.fire({
			title: 'Contacto',
			html: `
                <p>Para más información, puedes:</p>
                    📞 Llamar al <strong>+51 953 270 109</strong><br>
                    📧 Escríbenos a <strong>p.a.l.store.atencion@gmail.com</strong><br>	
					para poder ayudarte.
                </ul>
            `,
			icon: 'info',
			confirmButtonText: 'Cerrar'
		});
	});
	
	
	// Agrega el evento de clic para "Reportar un problema"
		problemasBtn.addEventListener('click', function(event) {
			event.preventDefault(); // Evita la acción predeterminada del enlace
			Swal.fire({
				title: 'Reportar un problema',
				html: `
	                <p>Si hay algo en esta página que no está funcionando como debería, 
					escríbenos con una descripción detallada del problema a nuestro correo:</p>
	                    <strong>p.a.l.store.atencion@gmail.com</strong><br>
	                </ul>
	            `,
				icon: 'info',
				confirmButtonText: 'Cerrar'
			});
		});
	

	// Agrega el evento de clic para "Nosotros"
	nosotrosBtn.addEventListener('click', function(event) {
		event.preventDefault(); // Evita la acción predeterminada del enlace
		Swal.fire({
			title: 'Nosotros',
			html: `
                <p>Somos una equipo de estudiantes de Diseño y Desarrollo de Software de CERTUS</p>
                <h4>Nuestro equipo:</h4><br>
                <p><strong>Ariel Camilo</strong>: Supervisor de pruebas/DB Admin</p>
				<p><strong>Sergio Colan</strong>: Full Stack Dev/Tester</p>
				<p><strong>Alessandro Malca</strong>: Desarrollador</p>
				<p><strong>Gerber Manrique</strong>: Desarrollador</p>
				<p><strong>Luis Carbonel</strong>: Desarrollador</p>
            `,
			icon: 'info',
			confirmButtonText: 'Cerrar'
		});
	});
});















