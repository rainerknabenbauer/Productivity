<script>
    import Project from './Project';
    import Button from './Button.svelte';
    import { createEventDispatcher } from 'svelte';

    const dispatch = createEventDispatcher();

    export let project;

    let xposition = 350;
    let yposition = 125;
    let isMoving = false;
    let isVisible = true;
    const host = window.location.hostname;

    function showReminder() {
        dispatch('showReminder');
	}

    function toggle() {
        var block = document.getElementById("reminder");
        isMoving = !isMoving;

        if (isMoving) {
            block.style.cursor = "grabbing";
        } else {
            block.style.cursor = "grab";
        }
    }

    function bindEmail() {
		fetch("http://" + host + ":8080/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: JSON.stringify(project)
        });
        showReminder();
	}

    // Calculate relative position of DIV
    document.addEventListener('mousemove', (event) => {
        if (isMoving) {
            var block = document.getElementById("reminder");
            xposition = event.clientX-(block.offsetWidth/2);
            yposition = event.clientY-(block.offsetHeight/2);
        }
    });
</script>

<style>
    .container {
        cursor: -webkit-grab; 
        cursor: grab;
        border: 1px solid darkgreen;
        min-width: 375;
        max-width: 500px;
        background-color: #efefef;
        border-radius: 3px;
        z-index: 5;

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
    .email {
        width: 100%;
        resize: none;
    }
    .email:focus {
        outline: none;
    }
</style>

{#if isVisible}
    <!-- The block gets attached to the mouse when you hold down left mouse button-->
    <div id="reminder" class="container" style="position: absolute; top: {yposition}px; left: {xposition}px">
        <div class="header w3-light-grey">
            <div class="options" on:click={showReminder}>&#10008;</div>
            <div class="details" on:mousedown={toggle} on:mouseup={toggle}>
                <div class="title w3-flat-wet-asphalt rainbow w3-serif">Link your eMail account to your project</div>
                <div class="shortDescription">Did you know you could link your eMail to your projects?</div>
                <div class="shortDescription">Never again lose your notes because you forgot the link.</div>
                <textarea class="email" bind:value={project.email}></textarea>
                <Button on:click={bindEmail} text="Set reminder"></Button>
            </div>
        </div>
    </div>
{/if}