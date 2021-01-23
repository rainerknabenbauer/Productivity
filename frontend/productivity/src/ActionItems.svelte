<script>
  import { createEventDispatcher } from "svelte";
  import Button from "./Button.svelte";
  import RotatingBlock from "./RotatingBlock.svelte";

  const dispatch = createEventDispatcher();
  export let project;
  let isTitleBeingEdited = false;

  function showReminder() {
    dispatch("showReminder");
  }

  function showFAQ() {
    dispatch("showFAQ");
  }

  function showTrashbin() {
    dispatch("showTrashbin");
  }

  function showHistory() {
    dispatch("showHistory");
  }

  function showFirstSteps() {
    dispatch("showFirstSteps");
  }

  function showPinboard() {
    dispatch("showPinboard");
  }

  function editTitle() {
    isTitleBeingEdited = !isTitleBeingEdited;
  }

  function saveProject() {
    editTitle();
    dispatch('saveProject', {
			text: JSON.stringify(project)
		});
  }
</script>


<header class="header">
    <RotatingBlock on:showTaskDetails />

    {#if isTitleBeingEdited}
      <input class="editName" type="text" id="fname" name="fname" bind:value={project.name}/>
      <button on:click={saveProject}>OK</button>
    {:else}
      <div class="projectName" on:click={editTitle}>{project.name}</div>
    {/if}
    
    <button
      class="w3-button w3-white actionItems right" on:click={showTrashbin}>
      <i class="fa fa-trash-o" /></button>
    <button
      class="w3-button w3-white w3-hide-small actionItems" on:click={showFAQ}>
      <i class="fa fa-question-circle-o" /></button>
    <button class="w3-button w3-white w3-hide-small actionItems" on:click={showHistory}>
      <i class="fa fa-heartbeat w3-margin-right" />History</button>
    <button class="w3-button w3-white w3-hide-small actionItems" on:click={showReminder}>
      <i class="fa  fa-envelope-o w3-margin-right" />Reminder</button>
    <button class="w3-button w3-hide-small actionItems" on:click={showPinboard}>Pinboard</button>
    <button
      class="w3-button w3-white w3-hide-small actionItems" on:click={showFirstSteps}>
      <i class="fa fa-vimeo-square w3-margin-right" />First steps</button>
  </header>


<style>
  .right {
    margin-right: 5px;
  }
  .actionItems {
    float: right;
    color: #636363;
    background-color: lightgrey;

    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
    font-family: "Open Sans";
  }

  .projectName {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    color: grey;
    float: left;
    margin-top: -2px;
  }
  .editName {
    height: 40px;
  }
  .header {
    background-color: #dddddd;
    height: 50px;
    padding-top: 5px;
    min-width: 500px;
    border: 1px solid transparent;
    border-radius: 4px;
  }
</style>
