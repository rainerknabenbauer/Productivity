<script>
    import { createEventDispatcher } from 'svelte';
    import EditTask from './EditTask.svelte'

    const dispatch = createEventDispatcher();
    export let task;

    let isMoving = false;
    let isVisible = true;
    const host = window.location.hostname;

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

    async function deleteTask(task) {
        isVisible = false;
        await fetch("http://" + host + ":8080/tasks", {
            method: 'DELETE',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(task)
        });
        dispatch('deleteTask')
    }

    function editTask() {
		dispatch('edit', {
			text: JSON.stringify(task)
        });
	}
        
    //send to server
    async function updateTask() {
        if (isVisible) {
            console.log("Updating Task");
                const response = await fetch("http://" + host + ":8080/tasks", {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json',
                        'Media-Type': "MediaType.APPLICATION_JSON"
                    },
                    body: JSON.stringify(task)
                });
        }
    }

    // Calculate relative position of DIV
    document.addEventListener('mousemove', (event) => {
        if (isMoving) {
            var block = document.getElementById(task.id);
            task.ui.xposition = event.clientX-(block.offsetWidth/2);
            task.ui.yposition = event.clientY-(block.offsetHeight/2);
            // task.ui.xposition = event.clientX - (task.ui.xposition - event.clientX)
            // task.ui.yposition = event.clientY - (task.ui.yposition - event.clientX)
            dispatch('move');
        }
    });
</script>

<style>
    .task {
        cursor: -webkit-grab; 
        cursor: grab;
        border: 1px solid darkgreen;
        min-width: 250px;
        max-width: 325px;
        z-index: 1;

        background-color: rgb(206, 206, 206);
        border-radius: 3px;

        -webkit-touch-callout: none;
        -webkit-user-select: none;
        -khtml-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }

    .options {
        float: right;
        font-size: 12pt;
        padding-right: 5px;
        color: white;
    }

    .options:hover {
        color: darkgrey;
        cursor: default;
    }

    .title {
        padding: 5px 7px;
        letter-spacing: 0.7px;
    }

    .shortDescription {
        margin-left: 5px;
    }

    .rainbow {
	margin: 0;
	color: #fff;
	background: linear-gradient(-45deg, #888888, #49343c, #575050, #343837 );
	background-size: 400% 400%;
	-webkit-animation: gradientBG 10s ease infinite;
	        animation: gradientBG 10s ease infinite;
}

@-webkit-keyframes gradientBG {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}
@keyframes gradientBG {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}

.dark {
    color: darkslategrey;
}
</style>

{#if isVisible}
    <!-- The block gets attached to the mouse when you hold down left mouse button-->
    <div class="task" id={task.id} style="position: absolute; top: {task.ui.yposition}px; left: {task.ui.xposition}px">
        <div class="header">
            <div class="options" on:click={deleteTask(task)}>&#10008;</div>
            <div class="details" on:mousedown={toggle} on:mouseup={toggle}>
                <div class="title w3-flat-wet-asphalt rainbow w3-serif">{task.title}</div>
                <div class="shortDescription">{task.description.shortDescription}
                    <div class="options dark" on:click={editTask}><EditTask /></div>
                </div>
            </div>
        </div>
    </div>
{/if}