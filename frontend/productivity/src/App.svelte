
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
			console.log("getProject: "+blabla)
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
			await fetch(uri + '/tasks/' + projectId)
							.then(response => result = response.json())
							.catch(error => alert(error));
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
</script>

<style>
	.grey {
		background-color: #dddddd;
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
		  <MoveableBlock {task} on:edit={e => editTask(e.detail.text)}/>
	  {/each}
  {/await}

  {#if isReminderVisible}
		{#await projectPromise then project}
			<Reminder {projectId} email={project.email} on:showReminder={showReminder}/>
		{/await}
  {/if}
  
  

<!-- End page content -->
</div>

<Greeting />


