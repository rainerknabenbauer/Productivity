<script>
    import { onMount } from "svelte";

    let url = window.location.href;

    onMount(async () => {
		new QRCode(document.getElementById("qrcode"), {
            text: url,
            width: 100,
            height: 100,
            // QRCode.CorrectLevel.L | QRCode.CorrectLevel.M | QRCode.CorrectLevel.H
            correctLevel : QRCode.CorrectLevel.H
    })});

</script>

<style>
    .lowerleft
    {
        margin-bottom: 7px;
        margin-left : 0px;
        position: fixed;
        bottom: 0;
        font-size: 8pt;
        opacity: 0.2;
        z-index: 0;
        text-align: center;
    }

    .lowerleft:hover {
        opacity: 0.8;
    }
</style>

<a href="https://play.google.com/store/apps/details?id=la.droid.qr.priva&hl=en_US&gl=US" target="_blank">
<div id="qrcode" class="lowerleft">Share on mobile</div></a>