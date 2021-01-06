<script>

    export let task;
    console.log(task);

    let isMoving = false;


    function toggle() {
        var block = document.getElementById(task.id);
        isMoving = !isMoving;

        if (isMoving) {
            block.style.cursor = "grabbing";
        } else {
            block.style.cursor = "grab";
            updateTask();
        }
    }

    //send to server
    async function updateTask() {
                console.log(JSON.stringify(task));
                const response = await fetch('http://localhost:8080/tasks', {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json',
                        'Media-Type': "MediaType.APPLICATION_JSON"
                    },
                    body: JSON.stringify(task)
                }).then(data => {
                    console.log(data)
                });
            }

    // Attach box to cursor
    document.addEventListener('mousemove', (event) => {
        if (isMoving) {
            var block = document.getElementById(task.id);
            block.style.position = "absolute";

            task.ui.xPosition = event.clientX-(block.offsetWidth/2);
            task.ui.yPosition = event.clientY-(block.offsetHeight/2);

            block.style.left = task.ui.xPosition+"px";
            block.style.top = task.ui.yPosition+"px";
        }
    });
</script>

<style>
    .task {
        position: absolute;
        top: 35pt;
        cursor: -webkit-grab; 
        cursor: grab;
        border: 1px solid darkgreen;
        min-width: 250px;
        max-width: 500px;
        background-color: #efefef;
        border-radius: 3px;
    }

    .options {
        float: right;
        font-size: 12pt;
        padding-right: 2px;
    }

    .options:hover {
        color: lightgrey;
        cursor: default;
    }

    .title {
        padding: 5px;
        background-image: linear-gradient(red, #efefef);
        letter-spacing: 1px;
    }

    .shortDescription {
        margin: 5px;
    }

</style>

<!-- The block gets attached to the mouse when you hold down left mouse button-->
<div class="task" id={task.id} on:mousedown={toggle} on:mouseup={toggle} >
    <div class="header">
        <div class="options">&#10008;</div>
		<div class="details">
			<div class="title">{task.title}</div>
			<div class="shortDescription">{task.description.shortDescription}</div>
        </div>
        <div class="options">&#9998;</div>
    </div>
</div>


