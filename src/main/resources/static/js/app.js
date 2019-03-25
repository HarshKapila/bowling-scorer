(function () {
    var gameId;
    var maxPins = 10;
    var dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 150,
        width: 350,
        modal: true,
        buttons: {
            "Create": createNewGame,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[0].reset();
        }
    });

    var form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        createNewGame();
    });
    var playerName = $("#name");

    var validatePinsHit = function (targetElement, maxValue) {
        var inputValue = $(targetElement).val();
        if (inputValue > maxValue || inputValue < -1) {
            $(targetElement).removeClass("valid").addClass("error");
            return false;
        } else {
            $(targetElement).removeClass("error").addClass("valid");
            return true;
        }
    };

    $("#pins-hit").on("input", function () {
        validatePinsHit(this, maxPins);
    });

    function gameEndView() {
        $(".in-game").hide();
        $(".after-game").show();
        hideError();
    }

    function inGameView() {
        $(".in-game").show();
        $(".after-game").hide();
        hideError();
    }

    function createNewGame() {
        resetScoreDisplay();
        $.ajax({
            type: "POST",
            url: "/game/create",
            data: JSON.stringify({
                "playerName": playerName.val()
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                hideError();
                $("#name-holder").text(response.playerName);
                gameId = response.gameId;
                inGameView();
                dialog.dialog("close");
            },
            error: function (response) {
                showErrorMessage(response);
            }
        });
    }

    function resetScoreDisplay() {
        $("#name-holder").text("");
        $("#rollScore td:not(#name-holder)").each(function () {
            $(this).text("")
        });
        $("#frameTotal td").each(function () {
            $(this).text("")
        });
    }


    $("#hit").click(function () {
        if (validatePinsHit($("#pins-hit"), maxPins)) {
            $.ajax({
                type: "PATCH",
                url: "/game/score/add/",
                data: JSON.stringify({
                    "gameId": gameId,
                    "pinsHit": $("#pins-hit").val()
                }),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (response) {
                    hideError();
                    displayGame(response);
                    if (response.complete) {
                        gameEndView();
                    }
                    dialog.dialog("close");
                },
                error: function (response) {
                    showErrorMessage(response);
                }
            });
        }
    });

    function displayGame(game) {
        var playerNameElement = $("#name-holder");
        var rollScoreElements = $("#rollScore td:not(#name-holder)");
        var frameScoreElements = $("#frameTotal td");

        playerNameElement.text(game.playerName);
        var frames = game.frames;
        var totalScore = 0;
        var frame;
        for (var frameIndex in frames) {
            frame = frames[frameIndex];
            displayFrame(frame, frameIndex, rollScoreElements);
            totalScore += Math.max(frame.first, 0) + Math.max(frame.second, 0) + Math.max(frame.extra, 0);
            displayFrameTotal(frameScoreElements, frameIndex, totalScore);
        }
    }

    function displayFrame(frame, frameIndex, rollScoreElements) {

        var strike = frame.first === 10;
        var spare = frame.first + frame.second === 10;
        var strikeIndex = frameIndex * 2 + 1;
        //last frame
        if (frameIndex === "9") {
            displayRoll(rollScoreElements, frameIndex * 2 + 1, frame.second);
            displayRoll(rollScoreElements, frameIndex * 2 + 2, frame.extra);
            strikeIndex = frameIndex * 2;
        }
        if (strike) {
            displayRoll(rollScoreElements, strikeIndex, "X");
        } else {
            displayRoll(rollScoreElements, frameIndex * 2, frame.first);
            if (spare) {
                displayRoll(rollScoreElements, frameIndex * 2 + 1, "/");
            } else {
                displayRoll(rollScoreElements, frameIndex * 2 + 1, frame.second);
            }
        }
    }

    function displayRoll(elements, position, value) {
        if (value === 10) {
            $(elements[position]).text("X");
        } else if (value === -1) {
            $(elements[position]).text("F");
        } else if (value || value === 0) {
            $(elements[position]).text(value);
        }
    }

    function displayFrameTotal(elements, position, value) {
        if (value || value === 0) {
            $(elements[position]).text(value);
        }
    }

    function showErrorMessage(response) {
        var message = response.responseJSON.errors ? response.responseJSON.errors[0].defaultMessage : response.responseJSON.message;
        $("#error-message").text(message);
        $(".error-container").show();
    }

    function hideError() {
        $(".error-container").hide();
    }

    gameEndView();


    $("#new-game").button().on("click", function () {
        dialog.dialog("open");
    });
})();