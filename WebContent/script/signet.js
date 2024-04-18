function callSignet() {
	document.getElementById("loginForm").submit();
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
	const modal = document.getElementsByClassName("icePnlPopVisa");
	modal[0].style.width = hide ? "300px" : "570px";
	modal[0].style.height = hide ? "140px" : "430px";
	const signet = document.getElementById("iframeFirma");
	signet.style.width = hide ? "250px" : "550px";
	signet.style.height = hide ? "140px" : "320px";
	const callSignetButton = document.getElementsByClassName("callSignetButton");
	callSignetButton[0].hidden = !hide;
}