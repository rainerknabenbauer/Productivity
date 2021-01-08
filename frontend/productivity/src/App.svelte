
<script>
	import { onMount } from 'svelte';
	import MoveableBlock from './MoveableBlock.svelte';
	import Button from './Button.svelte';
	import AddNote from './AddNote.svelte'

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

	function toggleAddNoteVisibility(event) {
		isAddNoteVisible = !isAddNoteVisible;
	}

	async function addNote() {
		tasksPromise = getTasks();
		isAddNoteVisible = !isAddNoteVisible;
	}
</script>


<Button text="Add note" on:click={toggleAddNoteVisibility} />

{#if isAddNoteVisible}
	<AddNote on:refresh={addNote} />
{/if}

{#await tasksPromise then tasks}
	{#each tasks as task (task.id)}
        <MoveableBlock {task}/>
    {/each}
{/await}



