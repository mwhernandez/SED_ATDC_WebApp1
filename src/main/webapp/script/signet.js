function showPdf(data) {
	const visualizacionDocumentoButton = document.getElementsByClassName("visualizacionDocumentoButton");
	visualizacionDocumentoButton[0].href = data;
	visualizacionDocumentoButton[0].click();
}

function callSignet() {
	document.getElementById("signetForm").submit();
	setupSignetForm(false);
	const signetContainer = document.getElementById("firma-container");
	signetContainer.style.background = "white";
	signetContainer.style.display = "block";
}

function hideSignet() {
	const signetContainer = document.getElementById("firma-container");
	signetContainer.style.display = "none";
	setupSignetForm(true);
}

function setSignetMessage(data) {
	console.log(data);
	const signetMessage = document.getElementById("signetMessage");
	signetMessage.value = data;
	const signetMessageButton = document.getElementsByClassName("signetMessageButton");
	signetMessageButton[0].click();
}

function setupSignetForm(hide) {
	const signet = document.getElementById("iframeFirma");
	signet.style.width = hide ? "250px" : "550px";
	signet.style.height = hide ? "240px" : "320px";
	const callSignetButton = document.getElementsByClassName("callSignetButton");
	callSignetButton[0].hidden = !hide;
}