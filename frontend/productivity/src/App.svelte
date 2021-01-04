
<script>
	import MoveableBlock from './MoveableBlock.svelte';
	import Button from './Button.svelte';
	import AddNote from './AddNote.svelte'

	let isAddNoteVisible = false;

	async function getTasks() {
		let json = [];
		const res = await fetch('http://localhost:8080/tasks')
							.then(response => json = response.json())
							.catch(error => json = JSON.parse('[{"id":"404","title":"Error","description":{"shortDescription":"Failed to connect to server.","longDescription":"Connection to server could not be established."},"endDate":"","priority":0}]'));

		return json;
    }
	let tasksQuery = getTasks();

	function toggleAddNoteVisibility(event) {
		isAddNoteVisible = !isAddNoteVisible;
	}

	function addNote() {
		alert("added");
	}
</script>


<Button text="Add note" on:click={toggleAddNoteVisibility} />

{#if isAddNoteVisible}
	<AddNote on:click={addNote} />
{/if}

{#await tasksQuery then tasks}
	{#each tasks as task (task.id)}
        <MoveableBlock {...task}/>
    {/each}
{/await}



