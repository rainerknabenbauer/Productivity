
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import AddNote from './AddNote.svelte'
	import Task from './Task.js';
	import Greeting from './Greeting.svelte';
	import Reminder from './Reminder.svelte';
	import Canvas from './Canvas.svelte';
	import Header from './Header.svelte';

	let isAddNoteVisible = false;
	let isReminderVisible = false;
	let tasksPromise = [];
	let task;
	const uri = "http://localhost:8080";
	const self = "http://localhost:5000";

	let projectPromise;
	let projectId = window.location.search.substr(1);

	onMount(async () => {
		tasksPromise = getTasks();
		projectPromise = getProject();
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
		var wrapper = document.getElementById("canvas-wrapper");
		var canvas = document.getElementById("canvas");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
		
		tasks.forEach(task => {
			context.beginPath();
			context.moveTo(window.innerWidth/2, window.innerHeight/2-wrapper.offsetTop, 0);
			context.lineTo(task.ui.xposition-wrapper.offsetLeft+175, task.ui.yposition-wrapper.offsetTop+25);
			context.strokeStyle = '#dddddd';
			context.stroke();
			context.closePath();
		})

	}
</script>

<style>

</style>

<!-- !PAGE CONTENT! -->
<div class="w3-main">
  <!-- Header -->
  <Header on:showAddNote={toggleAddNoteVisibility} on:showReminder={showReminder} />

  {#if isAddNoteVisible}
	  <AddNote on:refresh={addNote} {task} {projectId}/>
  {/if}
  
  {#await tasksPromise then tasks}
	  {#each tasks as task (task.id)}
			  <MoveableBlock {task} 
				  on:edit={e => editTask(e.detail.text)} 
				  on:move={e => drawLines(tasks)}/>
	  {/each}
  {/await}

  {#if isReminderVisible}
		{#await projectPromise then project}
			<Reminder {projectId} email={project.email} on:showReminder={showReminder}/>
		{/await}
  {/if}

<!-- End page content -->
</div>

<Canvas />


<Greeting />


