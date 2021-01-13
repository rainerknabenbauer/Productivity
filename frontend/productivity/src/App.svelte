
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import AddNote from './AddNote.svelte'
	import Filter from './Filter.svelte';
	import RotatingBlock from './RotatingBlock.svelte';
	import Task from './Task.js';
	import Greeting from './Greeting.svelte';

	let isAddNoteVisible = false;
	let tasksPromise = [];
	let task;
	let symbol = "☆";
	const uri = "http://localhost:8080";
	let project;

	onMount(async () => {
		project = window.location.search.substr(1);
		tasksPromise = getTasks();
	});

	async function getTasks() {
		let result = [];
		if (project === undefined || project === "") {
			await fetch(uri + '/tasks')
							.then(response => result = response.json())
							.catch(error => alert(error));
		} else {
			await fetch(uri + '/tasks/' + project)
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
      <Filter/>
    </div>
  </header>

  {#if isAddNoteVisible}
	  <AddNote on:refresh={addNote} {task} {project}/>
  {/if}
  
  {#await tasksPromise then tasks}
	  {#each tasks as task (task.id)}
		  <MoveableBlock {task} on:edit={e => editTask(e.detail.text)}/>
	  {/each}
  {/await}
  
  

<!-- End page content -->
</div>

<Greeting />


