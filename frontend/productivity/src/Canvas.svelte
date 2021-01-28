<script>
    import { createEventDispatcher, onMount } from 'svelte';
    const dispatch = createEventDispatcher();

    onMount(async () => {
        var wrapper = document.getElementById("canvas-wrapper");
        var canvas = document.getElementById("canvas");
        canvas.width = wrapper.clientWidth;
        canvas.height = wrapper.clientHeight;
        dispatch("canvasReady");
    })
</script>

<style>
    canvas {
        background-color: transparent;
        z-index: 0;
		float: left;
    }
	#canvas-wrapper {
		width: 100%;
		height: 85%;
	}
</style>

<div id="canvas-wrapper">
	<canvas id="canvas"/>
</div>