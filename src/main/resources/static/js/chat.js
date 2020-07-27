window.onload = init; //Al entrar en la página, se ejecutará cada segundo la funcion getMnesajes.
	function init() { //de esta forma, los nuevos mensajes del chat se irán actualizando en todas las páginas abiertas.
		setInterval('getMensajes()', 1000);
		var registrado = 0;
		var url = window.location.href;
        var url = new URL(url);
        var name = url.searchParams.get("nameuser");
        if(name==null){
         nombre = $('#nombre').val();
        }else{
           nombre = name;
        }

		registrado = 1;
		var fechaYHora;

		document.getElementById('aparecer').style.color = "#BF00DC"

	}

	function addO() { //Función encargada de almacenar en la "BD" la información nueva.

		var formData = {}; //En esta variable almacenaremos dichos datos
		var mensaje = $('#mensaje').val();
		if (mensaje !== "") {
			var f = new Date();
			fechaYHora = f.getDate() + "/" + (f.getMonth() + 1) + "/"
					+ f.getFullYear() + " " + f.getHours() + ":"
					+ f.getMinutes() + "<br>";
			formData['nombre'] = nombre; //Recogemos los valores almacenados en los id de nombre y mensaje
			formData['mensaje'] = mensaje;
			formData['fechaYHora'] = fechaYHora;

			$.ajax({ //Los enviamos a la "BD" (en localhost:8080/chatbd/) se irán almacenando todos los valores
				type : "POST",
				url : 'http://localhost:8080/chatbd/',
				dataType : 'json',
				async : true,
				// El objeto hay que convertirlo a texto
				data : JSON.stringify(formData),
				contentType : 'application/json'
			});
			document.getElementById("mensaje").value = ""; //"Vaciamos" el area de texto para que así, no aparezcan mensajes repetidos

		}
	}

	function getMensajes() { //Aquí accederemos a la "BD" para obtener la conversacion que figurará en el chat.

		$
				.ajax({ //Accedemos a localhost:8080/chatbd/, que es donde están almacenados los datos de la conversacion
					url : "http://localhost:8080/chatbd/search/listDeschat"
				})
				.then(
						function(data) {
							mensajes = "";
							document.getElementById("espacioChat").innerHTML = ""; //"Vaciamos" el area de texto para que así, no aparezcan mensajes repetidos
							$
									.each(
											data._embedded.chatbd,
											function(index, value) { //Recorremos cada uno de los elementos de localhost:8080/chatbd/
												mensajes += "<div class=\"chatUserName\">"
														+ value['nombre']
														+ "</div>"
														+ "<div class=\"chatMessage\">"
														+ value['mensaje']
														+ "</div>";
												mensajes += "<div align=\"right\">"
														+ value['fechaYHora']
														+ "</div>";
												mensajes += "<hr class=\"charHR\">";

												//Los "imprimimos" en el area de texto de la forma _$t nombre:mensaje
											});

							document.getElementById("espacioChat").innerHTML += mensajes;

						});
	}


