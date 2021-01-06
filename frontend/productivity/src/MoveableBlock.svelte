<script>
        export let id = undefined;
        export let title = undefined;
        export let description = undefined;
        export let endDate = undefined;
        export let priority = undefined;
        export let preDependency = undefined;
        export let postDependency = undefined;
        export let ui = undefined;

    let isActive = false;

    function toggle() {
        var block = document.getElementById(id);
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
            var block = document.getElementById(id);
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
<div class="task" id={id} on:mousedown={toggle} on:mouseup={toggle}>
    <div class="header">
        <div class="options">&#10008;</div>
		<div class="details">
			<div class="title">{title}</div>
			<div class="shortDescription">{description.shortDescription}</div>
        </div>
        <div class="options">&#9998;</div>
    </div>
</div>


