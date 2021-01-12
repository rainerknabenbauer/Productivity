
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import Button from './Button.svelte';
	import AddNote from './AddNote.svelte'

	let isAddNoteVisible = false;
	let isModalVisible = false;
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
	.filter {
		float: right;
	}
</style>

<!-- !PAGE CONTENT! -->
<div class="w3-main">

  <!-- Header -->
  <header id="portfolio" class="w3-light-grey">
    <div class="w3-section w3-bottombar w3-padding-16">
	  <Button text="Add note" on:click={toggleAddNoteVisibility} />
      <button class="w3-button w3-white w3-hide-small filter"><i class="fa fa-map-pin w3-margin-right"></i>Art</button>
	  <button class="w3-button w3-white w3-hide-small filter"><i class="fa fa-photo w3-margin-right"></i>Photos</button>
      <button class="w3-button w3-white filter"><i class="fa fa-diamond w3-margin-right"></i>Design</button>
	  <button class="w3-button w3-black filter">ALL</button>
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



