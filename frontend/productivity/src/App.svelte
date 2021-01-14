
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import AddNote from './AddNote.svelte'
	import Filter from './Filter.svelte';
	import RotatingBlock from './RotatingBlock.svelte';
	import Task from './Task.js';
	import Greeting from './Greeting.svelte';
	import Reminder from './Reminder.svelte';

	let isAddNoteVisible = false;
	let isReminderVisible = false;
	let tasksPromise = [];
	let task;
	let symbol = "☆";
	const uri = "http://localhost:8080";
	const self = "http://localhost:5000";

	let projectPromise;
	let project;
	let projectId = window.location.search.substr(1);

	onMount(async () => {
		tasksPromise = getTasks();
		projectPromise = getProject();

		var wrapper = document.getElementById("canvas-wrapper");
        var canvas = document.getElementById("canvas");
        canvas.width = wrapper.clientWidth;
		canvas.height = wrapper.clientHeight;
	});

	async function getProject() {
		let result = [];
		let projectId = window.location.search.substr(1);
		if (projectId === undefined || projectId === "") {
			let blabla = await fetch(uri + '/projects/new')
							.then(response => result = response.json())
							.catch(error => alert(error));
			location.assign(self + "/?" + blabla.projectId)
		} else {
			await fetch(uri + '/projects/' + projectId)
							.then(response => result = response.json())
							.catch(error => alert(error));
			
		}
		return result;

	}

	async function getTasks() {
		let projectId = window.location.search.substr(1);
		let result = [];
		if (!(projectId === undefined || projectId === "")) {
			let response = await fetch(uri + '/tasks/' + projectId)
							.then(response => result = response.json())
							.catch(error => alert(error));
			drawLines(await response);
		} 
		return result;
	}

	function toggleAddNoteVisibility() {
		isAddNoteVisible = !isAddNoteVisible;
		task = new Task();
		if(isAddNoteVisible) {
			symbol = "★";
		} else {
			symbol = "☆";
		}
	}

	function showReminder() {
		isReminderVisible = !isReminderVisible;
	}

	async function addNote() {
		tasksPromise = getTasks();
		isAddNoteVisible = !isAddNoteVisible;
	}

	function editTask(taskJson) {
		isAddNoteVisible = true;
		task = JSON.parse(taskJson);
	}

	async function drawLines(tasks) {
		console.log(tasks)
		tasks.forEach(task => {
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

	}
</script>

<style>
	.grey {
		background-color: #dddddd;
	}
	/* Scale canvas with resize attribute to full size */
    canvas {
        background-color: transparent;
        z-index: 0;
		float: left;
        width: 1900;
        height: 1400;
    }
	#canvas-wrapper {
		width: 100%;
		height: 100%;
	}
</style>

<!-- !PAGE CONTENT! -->
<div class="w3-main">
  <!-- Header -->
  <header class="grey">
    <div class="w3-section w3-bottombar w3-padding-16">
	  <RotatingBlock {symbol} on:click={toggleAddNoteVisibility} />
      <Filter on:showReminder={showReminder}/>
    </div>
  </header>

  {#if isAddNoteVisible}
	  <AddNote on:refresh={addNote} {task} {projectId}/>
  {/if}
  
  {#await tasksPromise then tasks}
	  {#each tasks as task (task.id)}
			  <MoveableBlock {task} 
				  on:edit={e => editTask(e.detail.text)} 
				  on:move={e => drawLines([JSON.parse(e.detail.text)])}/>
	  {/each}
  {/await}

  {#if isReminderVisible}
		{#await projectPromise then project}
			<Reminder {projectId} email={project.email} on:showReminder={showReminder}/>
		{/await}
  {/if}

<!-- End page content -->
</div>

<div id="canvas-wrapper">
	<canvas id="canvas"/>
</div>


<Greeting />


