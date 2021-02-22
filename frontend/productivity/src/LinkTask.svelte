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
        console.log(suggestions)
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
        outline: none;
    }

    .selection {
        width: 100%;
        height: 55%;
    }

    .unlink {
        font-size: 8pt;
        float: right;
        padding-right: 5px;
        padding-top: 2px;
    }
</style>

Search task title:
<!-- svelte-ignore a11y-autofocus -->
<textarea class="textarea" autofocus bind:value={search} on:input={createSuggestion}></textarea>
Select parent task: 
{#if Array.isArray(task.parentTasks) && task.parentTasks.length}
    <span class="unlink" on:click={() => task.parentTasks = []}>Remove parent</span>
{/if}
<select class="selection" multiple bind:value={selection}>
	{#each suggestions as suggestion (suggestion.id)}
		<option value={suggestion.id} on:click={() => task.parentTasks = selection}>
			{suggestion.title}
		</option>
	{/each}
</select>