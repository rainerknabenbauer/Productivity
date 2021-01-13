
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import AddNote from './AddNote.svelte'
	import Filter from './Filter.svelte';
	import RotatingBlock from './RotatingBlock.svelte';
	import Task from './Task.js';
	import Greeting from './Greeting.svelte';
	import Reminder from './Reminder.svelte';
	import Project from './Project.js';

	let isAddNoteVisible = false;
	let isReminderVisible = false;
	let tasksPromise = [];
	let task;
	let symbol = "☆";
	const uri = "http://localhost:8080";
	let projectPromise = getProject();

	onMount(async () => {
		tasksPromise = getTasks();
		projectPromise = getProject();
	});

	async function getProject() {
		let projectId = window.location.search.substr(1);
		let result = [];
		await fetch(uri + '/projects/' + projectId)
							.then(response => result = response.json())
							.catch(e => {result = "{}"});
		return result;

	}

	async function getTasks() {
		let result = [];
		if (projectPromise.projectId === undefined || projectPromise.projectId === "") {
			await fetch(uri + '/tasks')
							.then(response => result = response.json())
							.catch(error => alert(error));
		} else {
			await fetch(uri + '/tasks/' + projectPromise.projectId)
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

	function toggleReminder() {
		isReminderVisible = !isReminderVisible;
	}

	async function addNote() {
		tasksPromise = getTasks();
		isAddNoteVisible = !isAddNoteVisible;
	}

	function editTask(taskJson) {
		console.log("task: "+taskJson);
		isAddNoteVisible = true;
		task = JSON.parse(taskJson);
	}
</script>

<style>
	
</style>

<!-- !PAGE CONTENT! -->
<div class="w3-main">

  <!-- Header -->
  <header class="w3-light-grey">
    <div class="w3-section w3-bottombar w3-padding-16">
	  <RotatingBlock {symbol} on:click={toggleAddNoteVisibility} />
      <Filter on:showReminder={toggleReminder}/>
    </div>
  </header>

  {#if isAddNoteVisible}
	  <AddNote on:refresh={addNote} {task} projectId={projectPromise.projectId}/>
  {/if}
  
  {#await tasksPromise then tasks}
	  {#each tasks as task (task.id)}
		  <MoveableBlock {task} on:edit={e => editTask(e.detail.text)}/>
	  {/each}
  {/await}

  {#if isReminderVisible}
		{#await projectPromise then project}
			<Reminder projectId={project.projectId} email={project.email}/>
		{/await}
  {/if}
  
  

<!-- End page content -->
</div>

<Greeting />


