
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import Button from './Button.svelte';
	import AddNote from './AddNote.svelte'
	import Filter from './Filter.svelte';

	let isAddNoteVisible = false;
	let tasksPromise = [];

	onMount(async () => {
		tasksPromise = getTasks();
	});

	async function getTasks() {
		let result = [];
		
		await fetch('http://localhost:8080/tasks')
							.then(response => result = response.json())
							.catch(error => alert(error));

		return result;
	}

	function toggleAddNoteVisibility() {
		isAddNoteVisible = !isAddNoteVisible;
	}

	async function addNote() {
		tasksPromise = getTasks();
		isAddNoteVisible = !isAddNoteVisible;
	}
</script>
	
<style>
	.headline {
		background:-webkit-linear-gradient(90deg, rgb(38, 38, 38) 0%, rgba(0, 255, 0, 0) 80%);
    	background:-o-linear-gradient(90deg, rgb(38, 38, 38) 0%, rgba(0, 255, 0, 0) 80%);
    	background:-moz-linear-gradient(90deg, rgb(38, 38, 38) 0%, rgba(0, 255, 0, 0) 80%);
    	background:linear-gradient(90deg, rgb(38, 38, 38) 0%, rgba(0, 255, 0, 0) 80%);
	}

</style>

<!-- !PAGE CONTENT! -->
<div class="w3-main">

  <!-- Header -->
  <header class="headline">
    <div class="w3-section w3-padding-16">
	  <Button text="Add note" on:click={toggleAddNoteVisibility} />
      <Filter/>
    </div>
  </header>

  {#if isAddNoteVisible}
	  <AddNote on:refresh={addNote} />
  {/if}
  
  {#await tasksPromise then tasks}
	  {#each tasks as task (task.id)}
		  <MoveableBlock {task}/>
	  {/each}
  {/await}
  
  

<!-- End page content -->
</div>



