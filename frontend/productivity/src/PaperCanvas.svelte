<script>
    export let task;
    import { onMount } from "svelte";



    onMount(() => {
        var wrapper = document.getElementById("canvas-wrapper");
        var canvas = document.getElementById("canvas");
        var context = canvas.getContext("2d");
        context.beginPath();
        context.moveTo(0, 0);
        context.lineTo(task.ui.xposition-wrapper.offsetLeft+175, task.ui.yposition-wrapper.offsetTop+25);
        context.stroke();
        context.closePath();
        console.log("from "+ task.ui.xposition +" to " + (task.ui.yposition-100))
        console.log("line drawn for " + task.title)
    })


  </script>
  
  <style>
  </style>
  