
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

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-flat.css">
<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main">

  <!-- Header -->
  <header id="portfolio" class="w3-light-grey">
    <div class="w3-section w3-bottombar w3-padding-16">
	  <Button text="Add note" on:click={toggleAddNoteVisibility} />
      <span class="w3-margin-right w3-margin-left">Filter:</span> 
      <button class="w3-button w3-black">ALL</button>
      <button class="w3-button w3-white"><i class="fa fa-diamond w3-margin-right"></i>Design</button>
      <button class="w3-button w3-white w3-hide-small"><i class="fa fa-photo w3-margin-right"></i>Photos</button>
      <button class="w3-button w3-white w3-hide-small"><i class="fa fa-map-pin w3-margin-right"></i>Art</button>
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



