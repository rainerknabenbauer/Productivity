<script>
  import { createEventDispatcher } from "svelte";
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
    document.title = project.name;
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
      class="w3-button actionItems right active" on:click={showTrashbin}>
      <i class="fa fa-trash-o" /></button>
    <button
      class="w3-button w3-hide-small actionItems active" on:click={showFAQ}>
      <i class="fa fa-angle-down w3-margin-right" />More</button>
    <!--
    <button class="w3-button w3-white w3-hide-small actionItems" on:click={showHistory}>
      <i class="fa fa-heartbeat w3-margin-right" />History</button>
    -->
    <button class="w3-button w3-hide-small actionItems active" on:click={showReminder}>
      {#if project.email === null}
      <i class="fa fa-bell-o w3-margin-right" />
      {:else}
      <i class="fa fa-bell w3-margin-right" />
      {/if}
      Reminder</button>
    <button class="w3-button w3-hide-small actionItems inactive" on:click={showPinboard}>Pinboard</button>
    <button
      class="w3-button w3-hide-small actionItems active" on:click={showFirstSteps}>
      <i class="fa fa-question-circle-o w3-margin-right" />First steps</button>
  </header>


<style>
  .inactive {
    background-color: #bdcad9;
  }
  .active {
    background-color: #dadfe6;
  }
  .active:hover, .inactive:hover {
    background-color: #bdcad9;
  }
  .right {
    margin-right: 5px;
  }
  .actionItems {
    float: right;
    color: #636363;

    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
    font-family: "Open Sans";
  }

  .projectName {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    color: #636363;
    float: left;
    margin-top: -2px;
  }
  .editName {
    height: 40px;
  }
  .header {
    height: 50px;
    padding-top: 5px;
    min-width: 500px;
    border: 1px solid transparent;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.03) 0px 2px 5px 0px;
    background-color: #bdcad9;
    background-image: linear-gradient(315deg, #bdcad9 0%, #e1dada 74%);

  }
</style>
