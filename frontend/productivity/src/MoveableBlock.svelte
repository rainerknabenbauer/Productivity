<script>
    import { createEventDispatcher, onMount } from "svelte";
    import Session from './Session.js'

    const dispatch = createEventDispatcher();
    export let task;

    let isClicked = false;
    let isMoving = false;
    let isVisible = true;
    const host = window.location.hostname;
    let minMove;
    let startPositionX = 0;
    let startPositionY = 0;

    onMount(async () => {
        getThreshold();
        setStyle();
    });

    function getThreshold() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
        
        if (urlParams.has('threshold')) {
            minMove = urlParams.get('threshold');
            console.log("threshold set to " + minMove);
        } else {
            minMove = 2;
            console.log("threshold set to " + minMove);
        }
	}

    async function holdOn(event) {
        var block = document.getElementById(task.id);

        startPositionX = event.pageX;
        startPositionY = event.pageY;
        isClicked = true;

        block.style.cursor = "grabbing";
    }

    async function letGo() {
        var block = document.getElementById(task.id);
        isMoving = false;
        isClicked = false;

        block.style.cursor = "grab";
        updateTask();
    }

    async function markDeleted() {
        isVisible = false;
        task.isDeleted = true;
        updateTask();
        dispatch("deleteTask");
    }

    async function toggleActive() {
        task.isBeingWorkedOn = !task.isBeingWorkedOn;
        updateTask();
        setStyle();
    }

    async function setStyle() {
        var div = document.getElementById(task.id+"title");
        if (task.isBeingWorkedOn) {
            div.classList.remove('inactive');
            div.classList.add('active');
        } else {
            div.classList.remove('active');
            div.classList.add('inactive');
        }
    }

    function editTask() {
        dispatch("edit", {
            text: JSON.stringify(task),
        });
    }

    //send to server
    async function updateTask() {

        let token = getCookieValue("token");
        const session = new Session();
        session.projectId = task.projectId;
        session.token = token;

        const response = await fetch("https://" + host + ":8443/tasks", {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
                "Media-Type": "MediaType.APPLICATION_JSON",
                'Authorization': "Basic " + btoa(session)
            },
            body: JSON.stringify(task),
        });
    }

    function getCookieValue(name) {
        let result = document.cookie.match("(^|[^;]+)\\s*" + name + "\\s*=\\s*([^;]+)");
        return result ? result.pop() : "";
    }

    // Calculate relative position of DIV
    document.addEventListener("mousemove", (event) => {
        if (isClicked && jitterproof(event)) {
            isMoving = true;
        }

        if (isMoving) {
            var block = document.getElementById(task.id);
            task.ui.xposition = event.pageX - block.offsetWidth / 2;
            task.ui.yposition = event.pageY - block.offsetHeight / 2;
            dispatch("move");
        }
    });

    function jitterproof(event) {
        if (xThreshold(event) || yThreshold(event)) {
            return true;
        }
        return false;
    }

    function xThreshold(event) {
        return Math.abs(startPositionX - event.pageX) > minMove
    }

    function yThreshold(event) {
        return Math.abs(startPositionY - event.pageY) > minMove
    }
</script>

{#if isVisible}
    <!-- The block gets attached to the mouse when you hold down left mouse button-->
    <div
        class="task"
        id={task.id}
        style="position: absolute; top: {task.ui.yposition}px; left: {task.ui
            .xposition}px"
    >
        <div class="header">
            <div class="options" on:click={markDeleted}>&#10008;</div>
            {#if task.isBeingWorkedOn}
            <div class="options" on:click={toggleActive}><i class="fa  fa-star" /></div>
            {:else}
            <div class="options" on:click={toggleActive}><i class="fa fa-star-o " /></div>
            {/if}
            
            <div
                class="details"
                on:mousedown={(event) => holdOn(event)}
                on:mouseup={letGo}
            >   
                {#if task.isBeingWorkedOn}
                <div id="{task.id}title" class="title active">
                    {task.title}
                </div>
                <div class="shortDescription">
                    {task.description.shortDescription}
                    <div class="options dark" on:click={editTask}>
                        &#9998;
                    </div>
                </div>
                {:else}
                <div id="{task.id}title" class="title inactive">
                    {task.title}
                </div>
                <div class="shortDescription">
                    {task.description.shortDescription}
                    <div class="options dark" on:click={editTask}>
                        &#9998;
                    </div>
                </div>
                {/if}
            </div>
        </div>
    </div>
{/if}

<style>
    .task {
        cursor: -webkit-grab;
        cursor: grab;
        border: 1px solid rgb(148, 161, 123);
        min-width: 250px;
        max-width: 325px;
        z-index: 1;
        opacity: 0.98;

        background-color: rgb(241, 234, 234);
        border-radius: 3px;

        -webkit-touch-callout: none;
        -webkit-user-select: none;
        -khtml-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;

        box-shadow: 0 3px 5px 0 rgba(0,0,0,.1);
    }

    .options {
        float: right;
        font-size: 12pt;
        min-width: 25px;
        color: white;
        text-align: center;
        cursor: default;
    }

    .options:hover {
        color: darkgrey;
    }

    .title {
        padding: 5px 7px;
        letter-spacing: 0.5px;
        font-size: 10pt;
    }

    .shortDescription {
        margin-left: 5px;
        letter-spacing: 0.6px;
        font-size: 10pt
    }

    .inactive {
        margin: 0;
        color: #fff;
        background: linear-gradient(-45deg, #686868, #46533c, #686060, #545a59);
        background-size: 400% 400%;
        -webkit-animation: gradientBG 10s ease infinite;
        animation: gradientBG 10s ease infinite;
    }

    .active {
        margin: 0;
        color: #fff;
        background: linear-gradient(-45deg, #5f5f5f, #857c4e, #b1a279, #747444);
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
