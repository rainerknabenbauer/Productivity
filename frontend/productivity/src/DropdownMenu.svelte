<script>
    let isActive = false;

    function toggle() {
        isActive = !isActive;

        var block = document.getElementById('dropdownMenu');
        block.style.position = "absolute";

        if (isActive) {
            block.style.top = "-75px";
        } else {
            block.style.top = "-135px";
        }
    }
</script>

<style>
    div {
        position: absolute;
        left: 45%;
        top: -135px;
        width: 150px;
        height: 150px;
        background-color: green;
        -webkit-transform: rotate(45deg);
        -moz-transform: rotate(45deg);
        -o-transform: rotate(45deg);
        transform: rotate(45deg);
        animation-name: example;
        animation-duration: 1.5s;
    }

    @keyframes example {
    50% {background-color:#45d169; left:45%; top:-120px;}
    100% {background-color: green; left:45%; top:-135px;}
    }
  </style>


  <div id="dropdownMenu" on:mouseenter={toggle} on:mouseleave={toggle}>
        Walla!
  </div>

