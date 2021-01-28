<script>
    import { createEventDispatcher } from "svelte";
    import Button from "./Button.svelte";
    const dispatch = createEventDispatcher();
</script>


<style>
    .noProjectFound {
        padding-bottom: 5px;
    }
    .wrapper {
        padding: 70px 0;
          text-align: center;
    }
</style>

<div class="wrapper">
	<div class="noProjectFound">No project reference found.<br>Please consider creating a project.</div>
	<Button text="Create project" on:click={() => dispatch("createProject")} />
</div>