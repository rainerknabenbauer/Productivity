<script>
    import EditTask from './EditTask.svelte'

    export let task;
    console.log(task);

    let isMoving = false;
    let isVisible = true;

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

    
    
    
    
    
    
    // MERGE DELETE TASK AND UPDATE TASK ---- SWITCH STATEMENT






    async function deleteTask(task) {
        isVisible = false;
        console.log("Deleting Task");
        const response = await fetch('http://localhost:8080/tasks', {
            method: 'DELETE',
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
        
    //send to server
    async function updateTask() {
        if (isVisible) {
            console.log("Updating Task");
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
    }

    // Calculate relative position of DIV
    document.addEventListener('mousemove', (event) => {
        if (isMoving) {
            var block = document.getElementById(task.id);
            task.ui.xposition = event.clientX-(block.offsetWidth/2);
            task.ui.yposition = event.clientY-(block.offsetHeight/2);
        }
    });
</script>

<style>
    .task {

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

{#if isVisible}
    <!-- The block gets attached to the mouse when you hold down left mouse button-->
    <div class="task" id={task.id} on:mousedown={toggle} on:mouseup={toggle} style="position: absolute; top: {task.ui.yposition}px; left: {task.ui.xposition}px">
        <div class="header">
            <div class="options" on:click={deleteTask(task)}>&#10008;</div>
            <div class="details">
                <div class="title">{task.title}</div>
                <div class="shortDescription">{task.description.shortDescription}</div>
            </div>
            <div class="options"><EditTask /></div>
        </div>
    </div>
{/if}