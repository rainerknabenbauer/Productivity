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
	article {
		background-color: #fff;
		border: 1px #ccc solid;
		border-radius: 4px;
		padding: 1rem;
	}

    article:hover {
        background-color: lightgrey;
        border: 1px darkolivegreen solid;
    }

	.header {
		align-items: center;
		display: flex;
	}

	.details {
		flex: 1 1 auto;
		margin-left: 0.5rem
	}

    .priority {
        border: 2px solid grey;
        background-color: red;
        border-radius: 5px;
        width: 1em;
        height: 1em;
    }

	h4 {
		margin: 0;
	}
</style>

<article id={task.id} on:mousedown={toggle} on:mouseup={toggle}>
	<div class="header">
		<div class="priority"/>
		<div class="details">
            <h4>{task.title}</h4>
            {task.description.shortDescription}
		</div>
	</div>
	<div class="body">
		<slot></slot>
	</div>
</article>

<!-- The block gets attached to the mouse when you hold down left mouse button -->
