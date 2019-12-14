$(document).ready(function () {
    $('select[name="batiment"]').on('change', function () {
        var bat_id = $(this).val();
        if (bat_id != "") {
            $('#sallesfield').css("visibility", "visible");
            if (bat_id) {
                $.ajax({
                    url: '/find' + bat_id,
                    type: "GET",
                    dataType: "json",
                    beforeSend: function () {
                        $('#live_loading').css("visibility", "visible");
                    },
                    success: function (data) {
                        $.each(data, function (key, value) {});
                    },
                    complete: function (data) {
                        $('#live_loading').css("visibility", "hidden");
                    }
                });
            } else {
                $('select[name="salles"]').empty();
            }
        } else {
            $('#sallesfield').css("visibility", "hidden");
        }
    });
});
