<script>
    import { createEventDispatcher } from 'svelte';
    const dispatch = createEventDispatcher();
    
    function showReminder() {
		dispatch('showReminder');
	}

</script>

<style>
    .filter {
		float: right;
        color: #636363;
        background-color: lightgrey;

        text-align: center;
        letter-spacing: 1px;
        text-decoration: none;
        font-family: 'Open Sans';
	}
</style>


    <button class="w3-button w3-white w3-hide-small filter w3-margin-right"><i class="fa fa-trash-o"></i></button>
    <button class="w3-button w3-white w3-hide-small filter"><i class="fa fa-odnoklassniki w3-margin-right"></i>Recommendations</button>
    <button class="w3-button w3-white filter"><i class="fa fa-heartbeat w3-margin-right"></i>History</button>
    <button class="w3-button w3-white filter" on:click={showReminder}><i class="fa  fa-envelope-o w3-margin-right"></i>Reminder</button>
    <button class="w3-button filter">Pinboard</button>
