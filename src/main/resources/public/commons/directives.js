bdtApp.directive("runoobDirective", function() {
    return {
        template : "<h1>自定义指令!</h1>"
    };
})
.directive("ngTouchstart", function () {
    return {
        controller: ["$scope", "$element", function ($scope, $element) {
            $element.bind("touchstart mousedown", function (event) {
                var method = $element.attr("ng-touchstart");
                $scope.$apply(method);
            });
        }]
    }
})
.directive("ngTouchmove", function () {
    return {
        controller: ["$scope", "$element", function ($scope, $element) {
            $element.bind("touchstart", onTouchStart);
            function onTouchStart(event) {
                event.preventDefault();
                $element.bind("touchmove", onTouchMove);
                $element.bind("touchend", onTouchEnd);
            }
            function onTouchMove(event) {
                var method = $element.attr("ng-touchmove");
                $scope.$apply(method);
            }
            function onTouchEnd(event) {
                event.preventDefault();
                $element.unbind("touchmove", onTouchMove);
                $element.unbind("touchend", onTouchEnd);
            }

        }]
    }
})
.directive("ngTouchend", function () {
    return {
        controller: ["$scope", "$element", function ($scope, $element) {
            $element.bind("touchend mouseup",ngTouchend );
            function ngTouchend (event) {
                var method = $element.attr("ng-touchend");
                $scope.$apply(method);
            }
        }]
    }
});