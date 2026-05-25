INSERT INTO questions (id,text,type,profession_id,topic_id,explanation,source_url) VALUES
      (
          1,
          'Каким будет результат console.log(typeof null)?',
          'SINGLE',
          1,
          1,
          'В JavaScript выражение typeof null возвращает "object". Это историческая ошибка языка, оставленная для обратной совместимости.',
          'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/typeof'
      ),
      (
          4,
          'Какие из перечисленных значений являются ложными (falsy) в JavaScript?',
          'MULTIPLE',
          1,
          1,
          'Falsy-значения в JavaScript преобразуются к false в логическом контексте. К ним относятся 0, пустая строка "", null, undefined, NaN и false.',
          'https://developer.mozilla.org/en-US/docs/Glossary/Falsy'
      ),
      (
          6,
          'Назовите тип данных, который используется для хранения уникальных значений, появившийся в ES6.',
          'TEXT',
          1,
          1,
          'Set — встроенная структура данных JavaScript, появившаяся в ES6. Она хранит только уникальные значения.',
          'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Set'
      ),
      (
          9,
          'Каков будет порядок вывода? console.log(''1''); setTimeout(() => console.log(''2''), 0); Promise.resolve().then(() => console.log(''3'')); console.log(''4'');',
          'SINGLE',
          1,
          2,
          'Сначала выполняется синхронный код: 1 и 4. Затем микротаски Promise: 3. После этого макротаски setTimeout: 2. Итоговый порядок: 1, 4, 3, 2.',
          'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Event_loop'
      ),
      (
          12,
          'Назовите метод Promise, который выполняется, когда все переданные промисы завершены (resolved или rejected).',
          'TEXT',
          1,
          2,
          'Promise.allSettled() дожидается завершения всех промисов независимо от того, были они выполнены успешно или с ошибкой.',
          'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise/allSettled'
      ),
      (
          14,
          'Какой хук используется для выполнения побочных эффектов в функциональном компоненте?',
          'SINGLE',
          1,
          3,
          'Хук useEffect используется для побочных эффектов: запросов к API, подписок, таймеров, работы с DOM и других операций вне рендера.',
          'https://react.dev/reference/react/useEffect'
      ),
      (
          17,
          'Какие хуки относятся к встроенным хукам React?',
          'MULTIPLE',
          1,
          3,
          'useEffect, useMemo и useCallback являются встроенными хуками React. useFetch и useLocalStorage не входят в стандартный API React.',
          'https://react.dev/reference/react'
      ),
      (
          20,
          'Как называется синтаксическое расширение JavaScript, которое позволяет писать HTML-подобный код внутри React-компонентов?',
          'TEXT',
          1,
          3,
          'JSX (JavaScript XML) позволяет писать HTML-подобный синтаксис внутри JavaScript-кода React-компонентов.',
          'https://react.dev/learn/writing-markup-with-jsx'
      ),
      (
          22,
          'Что выведет console.log(document.getElementById(''test'').innerHTML) если элемент не найден?',
          'SINGLE',
          1,
          4,
          'document.getElementById() вернет null, если элемент не найден. Попытка обратиться к innerHTML у null вызовет TypeError.',
          'https://developer.mozilla.org/en-US/docs/Web/API/Document/getElementById'
      ),
      (
          25,
          'Назовите свойство объекта event, которое содержит элемент, на котором событие было изначально вызвано.',
          'TEXT',
          1,
          4,
          'Свойство event.target содержит DOM-элемент, на котором событие произошло первоначально.',
          'https://developer.mozilla.org/en-US/docs/Web/API/Event/target'
      ),
      (
          26,
          'Какой командой Git можно изменить последний коммит, не создавая новый?',
          'SINGLE',
          1,
          5,
          'Команда git commit --amend позволяет изменить последний коммит без создания нового коммита.',
          'https://git-scm.com/docs/git-commit#Documentation/git-commit.txt---amend'
      ),
      (
          27,
          'Назовите файл, который является точкой входа для npm-пакета и описывает его зависимости.',
          'TEXT',
          1,
          5,
          'Файл package.json содержит метаданные npm-пакета, зависимости, скрипты и настройки проекта.',
          'https://docs.npmjs.com/cli/v10/configuring-npm/package-json'
      ),
      (
          29,
          'Какой тип в TypeScript означает, что переменная может быть любого типа и компилятор отключает проверки?',
          'SINGLE',
          1,
          6,
          'Тип any отключает проверки типов TypeScript и позволяет присваивать переменной любые значения.',
          'https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#any'
      ),
      (
          30,
          'Назовите оператор TypeScript, который используется для объединения типов.',
          'TEXT',
          1,
          6,
          'Для объединения типов в TypeScript используется оператор |, который создает union type.',
          'https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#union-types'
      );