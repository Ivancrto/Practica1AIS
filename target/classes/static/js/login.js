function addUser() { // Función encargada de almacenar en la "BD" la
	// información nueva.

	var username = $('#usernameR').val();
	var pass = $('#passwordR').val();
	var b = true;

	var user = {
		"nameUser" : username,
		"password" : pass
	}

	$.ajax({
		url : "http://localhost:8080/users/" + username,
		success : function() {
			alert("User already registered!");
		},
		error : function() {
			$.ajax({
				type : "POST",
				url : 'http://localhost:8080/users/',
				dataType : 'json',
				async : true,
				// El objeto hay que convertirlo a texto
				data : JSON.stringify(user),
				contentType : 'application/json',
				success : function() { location.href = "menuprincipal?nameuser=" + username; }
			});
		}
	});

}

function iniciarUser() {

	var username = $('#username').val();
	var passwordUser = $('#password').val();
	$.ajax({
		url : "http://localhost:8080/users/" + username,
		error : function() {
			alert("Wrong username or password");
		}
	}).then(function(data) {
		if (passwordUser == data.password) {
			location.href = "menuprincipal?nameuser=" + username;
		} else {
			alert("Wrong password");
		}
	});

}
