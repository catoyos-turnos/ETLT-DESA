/* escribe correo */
function gen_mail_to_link(lhs, rhs, subject) {
	$("#correo-par").html(' ');
	$("#correo-par").append('<a href=\"mailto:' + lhs + '@' + rhs
			+ '?subject=\'' + subject + '\'\"> catoyos</a>. 2016');
}

$(function() {
	gen_mail_to_link('catoyos+ETLT', 'gmail.com', 'Sobre ETLT...');
});