<script>
    import Button from './Button.svelte';

    export let id = undefined;
    export let title = undefined;
    export let description = {
        shortDescription: undefined,
        longDescription: undefined,
        additionalNotes: undefined
    }
    export let endDate = undefined;
    export let priority = undefined;
    export let preDependency = undefined;
    export let postDependency = undefined;
    export let ui = undefined;
</script>

<style>
    .main {
        background-color: lightgrey;
        position: absolute;
        z-index: 10;
        display: grid;
        grid-template-columns: auto auto;
        border: 1px solid darkgreen;
    }

    .name {
        border: 1px solid darkgreen;
        padding: 3px;
        letter-spacing: 1px;
    }

    .input {
        border: 1px solid darkgreen;
        padding: 0px;
        margin: 0px;
        min-width: 200px;
    }

    .textarea {
        margin: 0px;
    }

</style>

<div class="main">
    <div class="name">Titel</div>
    <div class="input"><textarea class="textarea" bind:value={title}></textarea></div>

    <div class="name">Short Description</div>
    <div class="input"><textarea class="textarea" bind:value={description.shortDescription}></textarea></div>

    <div class="name">Long Description</div>
    <div class="input"><textarea class="textarea" bind:value={description.longDescription}></textarea></div>

    <div class="name">Additional notes</div>
    <div class="input"><textarea class="textarea" bind:value={description.additionalNotes}></textarea></div>

    <div class="name">Time period</div>   <!-- Give relative period, eg 6 weeks, calculate exakt date from the input-->
    <div class="input">[(1-24][h]<br>[_] weeks<br>...</div>
    
    <div class="name">Priority</div>
    <div class="input" type="number"><textarea class="textarea" bind:value={priority}></textarea></div>

    <div class="name">Leading tasks</div>
    <div class="input"><textarea class="textarea" bind:value={preDependency}></textarea></div>

    <div class="name">Following Tasks</div>
    <div class="input"><textarea class="textarea" bind:value={postDependency}></textarea></div>
    
    <div>Add Task</div>
    <div class=""><Button text="add" on:click/></div>
</div>


