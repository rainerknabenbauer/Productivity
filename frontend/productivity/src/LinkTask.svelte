<script>
    export let tasks;
    export let task;

    let search;
    let suggestions = [];
    let selection = []

    function createSuggestion() {
        suggestions = tasks.filter(task => 
            task.title.toLowerCase().includes(search.toLowerCase()) &&
            !task.isDeleted
        )
    }

    function saveTask() {
        task.parentTasks = selection;
    }
</script>

<style>
    .textarea {
        margin: 0px;
        padding: 4px 7px 4px 7px;
        width: 100%;
        resize: none;
    }

    .selection {
        width: 100%
    }
</style>

<!-- svelte-ignore a11y-autofocus -->
<textarea class="textarea" autofocus bind:value={search} on:input={createSuggestion}></textarea>

<select class="selection" multiple bind:value={selection}>
	{#each suggestions as suggestion (suggestion.id)}
		<option value={suggestion.id}>
			{suggestion.title}
		</option>
	{/each}
</select>

<button on:click={saveTask}>link</button>