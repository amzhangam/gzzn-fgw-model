function tupian(idt) {
	$("img[id^='xiaotu']").each(function() {
				$(this).attr("src", "images/ico06.gif");
			});
	$("#xiaotu" + idt).attr("src", "images/ico05.gif");

}

function list(idstr) {
	var name1 = "subtree" + idstr;
	var name2 = "img" + idstr;
	var objectobj = document.all(name1);
	var imgobj = document.all(name2);

	if (objectobj.style.display == "none") {
		$("table[id^='subtree']").each(function() {
					$(this).css("display", "none");
				});
		$("img[id^='img']").each(function() {
					$(this).attr("src", "images/ico04.gif");
				});
		objectobj.style.display = "";
		imgobj.src = "images/ico03.gif";
	} else {
		$("table[id^='subtree_s']").each(function() {
					$(this).css("display", "none");
				});
		objectobj.style.display = "none";
		imgobj.src = "images/ico04.gif";
	}
}
