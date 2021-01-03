
<script>
	import MoveableBlock from './MoveableBlock.svelte';

	async function getTasks() {
		const res = await fetch('http://localhost:8080/tasks');
        return await res.json();
    }

	let tasksQuery = getTasks();
</script>

<style></style>

{#await tasksQuery then tasks}
	{#each tasks as task (task.id)}
        <MoveableBlock {task}/>
    {/each}
{/await}



