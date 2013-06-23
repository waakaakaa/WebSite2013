var url = window.location.href;
document.write('<div class="navbar navbar-inverse navbar-fixed-top">');
document.write('<div class="navbar-inner">');
document.write('<div class="container">');
document
		.write('<button type="button" class="btn btn-navbar" data-toggle="collapse"	data-target=".nav-collapse">');
document.write('<span class="icon-bar"></span>');
document.write('<span class="icon-bar"></span>');
document.write('<span class="icon-bar"></span>');
document.write('</button>');
document.write('<a class="brand" href="/">X. Zhang\'s Website</a>');
document.write('<div class="nav-collapse collapse">');
document.write('<ul class="nav">');
document.write('<li><a href="/logout">Logout</a></li>');
if (url.indexOf('html') < 0) {
	document.write('<li class="active"><a href="/">Index</a></li>');
} else {
	document.write('<li><a href="/">Index</a></li>');
}
if (url.indexOf('/agenda.html') > 0) {
	document.write('<li class="active"><a href="/agenda.html">Agenda</a></li>');
} else {
	document.write('<li><a href="/agenda.html">Agenda</a></li>');
}
if (url.indexOf('/thesis.html') > 0) {
	document.write('<li class="active"><a href="/thesis.html">Thesis</a></li>');
} else {
	document.write('<li><a href="/thesis.html">Thesis</a></li>');
}
if (url.indexOf('/blog.html') > 0) {
	document.write('<li class="active"><a href="/blog.html">Blog</a></li>');
} else {
	document.write('<li><a href="/blog.html">Blog</a></li>');
}
document.write('</ul></div></div></div></div>');