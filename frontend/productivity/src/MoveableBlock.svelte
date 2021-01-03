<script>
    export let task;

    let isActive = false;

    function toggle() {
        var block = document.getElementById(task.id);
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
            var block = document.getElementById(task.id);
            block.style.position = "absolute";
            block.style.left = event.clientX-25+"px";
            block.style.top = event.clientY-25+"px";
        }
    });
</script>

<style>
    div {
        position: absolute;
        top: 15%;
        width: 50px;
        height: 50px;
        cursor: -webkit-grab; 
        cursor: grab;
        border-bottom-width: 1px;
        border-color: black;
    }
</style>

<!-- The block gets attached to the mouse when you hold down left mouse button -->
<div id={task.id} on:mousedown={toggle} on:mouseup={toggle}>
    {isActive}
</div>

