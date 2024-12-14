

function olvideContrasena() {
	Swal.fire({
		title: 'Recuperación de contraseñas',
		text: 'Ponte en contacto con nosotros a través del siguiente correo: p.a.l.store.atencion@gmail.com',
		icon: 'warning',
		confirmButtonText: 'Aceptar',
		background: '#ffffff', // Cambia el color de fondo de la alerta (un tono azul claro)
	});
}


function problema() {

	Swal.fire(
		{
			title: 'Reportar un problema',
			html: `
		                <p>Si hay algo en esta página que no está funcionando como debería, 
						escríbenos con una descripción detallada del problema a nuestro correo:</p>
		                    <strong>p.a.l.store.atencion@gmail.com</strong><br>
		                </ul>
		            `,
			icon: 'info',
			confirmButtonText: 'Cerrar'


		}
	)
}


