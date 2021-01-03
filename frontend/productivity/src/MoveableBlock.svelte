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
            block.style.left = event.clientX-(block.offsetWidth/2)+"px";
            block.style.top = event.clientY-(block.offsetHeight/2)+"px";
        }
    });
</script>

<style>
    .task {
        position: absolute;
        top: 15%;
        cursor: -webkit-grab; 
        cursor: grab;
        border: 1px solid darkgreen;
        min-width: 250px;
        max-width: 500px;
        background-color: #efefef;
        border-radius: 3px;
    }

    .title {
        padding: 5px;
        background-image: linear-gradient(red, #efefef);
    }

    .shortDescription {
        margin: 5px;
    }

</style>

<!-- The block gets attached to the mouse when you hold down left mouse button -->
<div class="task" id={task.id} on:mousedown={toggle} on:mouseup={toggle}>
    <div class="header">
		<div class="details">
			<div class="title">{task.title}</div>
			<div class="shortDescription">{task.description.shortDescription}</div>
		</div>
    </div>
</div>


