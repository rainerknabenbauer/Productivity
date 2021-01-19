<script>
    import { createEventDispatcher } from 'svelte';
    import RotatingBlock from './RotatingBlock.svelte';

    const dispatch = createEventDispatcher();
    
    function showReminder() {
		  dispatch('showReminder');
    }
    
    function showFAQ() {
      dispatch('showFAQ')
    }

    function showTrashbin() {
      dispatch('showTrashbin')
    }
</script>

<style>
    .actionItems {
		float: right;
        color: #636363;
        background-color: lightgrey;

        text-align: center;
        letter-spacing: 1px;
        text-decoration: none;
        font-family: 'Open Sans';
  }
  .grey {
		background-color: #dddddd;
	}
</style>

<header class="grey">
  <div class="w3-section w3-bottombar w3-padding-16">
    <RotatingBlock on:showTaskDetails/>
    <button class="w3-button w3-white w3-hide-small actionItems w3-margin-right" on:click={showTrashbin}><i class="fa fa-trash-o"></i></button>
    <!-- Lost link? next line with ? -->
    <button class="w3-button w3-white w3-hide-small actionItems" on:click={showFAQ}><i class="fa fa-question-circle-o"></i></button>
    <button class="w3-button w3-white w3-hide-small actionItems"><i class="fa   fa-ils w3-margin-right"></i>Statistics</button>
    <button class="w3-button w3-white actionItems"><i class="fa fa-heartbeat w3-margin-right"></i>History</button>
    <button class="w3-button w3-white actionItems" on:click={showReminder}><i class="fa  fa-envelope-o w3-margin-right"></i>Reminder</button>
    <button class="w3-button actionItems">Pinboard</button>
  </div>
</header>