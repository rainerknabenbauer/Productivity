<script>
    let isActive = false;

    function toggle() {
        var block = document.getElementById('block');
        isActive = !isActive;

        if (isActive) {
            block.style.cursor = "grabbing";
        } else {
            block.style.cursor = "grab";
        }
    }

    // Attach box to cursor
    document.addEventListener('mousemove', (event) => {
        if (isActive) {
            var block = document.getElementById('block');
            block.style.position = "absolute";
            block.style.left = event.clientX-25+"px";
            block.style.top = event.clientY-25+"px";
        }
    });
</script>

<style>
    div {
        background-color: red;
        position: absolute;
        top: 15%;
        width: 50px;
        height: 50px;
        border-radius: 25px;
        display: grid;
        place-items: center;
        cursor: -webkit-grab; 
        cursor: grab;
    }
</style>

<!-- The block gets attached to the mouse when you hold down left mouse button -->
<div id='block' on:mousedown={toggle} on:mouseup={toggle}>
    {isActive}
</div>

