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

<header class="grey">
  <div class="w3-section w3-bottombar w3-padding-16">
    <RotatingBlock on:showTaskDetails />

    {#if isTitleBeingEdited}
      <input class="editName" type="text" id="fname" name="fname" bind:value={project.name}/>
      <Button text="OK" on:click={saveProject}/>
    {:else}
      <div class="slogan" on:click={editTitle}>{project.name}</div>
    {/if}
    
    <button
      class="w3-button w3-white w3-hide-small actionItems w3-margin-right" on:click={showTrashbin}>
      <i class="fa fa-trash-o" /></button>
    <!-- Lost link? next line with ? -->
    <button
      class="w3-button w3-white w3-hide-small actionItems" on:click={showFAQ}>
      <i class="fa fa-question-circle-o" /></button>
    <button class="w3-button w3-white actionItems" on:click={showHistory}>
      <i class="fa fa-heartbeat w3-margin-right" />History</button>
    <button class="w3-button w3-white actionItems" on:click={showReminder}>
      <i class="fa  fa-envelope-o w3-margin-right" />Reminder</button>
    <button class="w3-button actionItems">Pinboard</button>
    <button
      class="w3-button w3-white w3-hide-small actionItems" on:click={showFirstSteps}>
      <i class="fa fa-vimeo-square w3-margin-right" />First steps</button>
  </div>
</header>

<style>
  .actionItems {
    float: right;
    color: #636363;
    background-color: lightgrey;

    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
    font-family: "Open Sans";
  }
  .grey {
    background-color: #dddddd;
  }
  .slogan {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    display: inline;
    color: grey;
    position: relative;
    top: 7px;
    left: 12px;
  }
  .editName {
    height: 40px;
  }
</style>
