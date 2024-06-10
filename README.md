
# clean-code-aau
622.060 (24S) Clean Code - Alpen Adria Universität Klagenfurt

## Assignment 1:
The task of this assignment is to develop a Web-Crawler in Java, which provides a compact overview of the given website and linked websites in a given language by only listing the headings and the links. The attached example-report.md shows an example of how the overview could look (feel free to improve the suggested layout).  

#### Must have features

How to start the application:

- your crawler must have a Main class which is the entry point to your application
- after the start, the user must then input arguments to the command line

The crawler MUST implement at least the following features:

- input the URL, the depth of websites to crawl, the domain(s) of websites to be crawled, and the target language to the command line
- create a compact overview of the crawled websites in a specified target language (e.g. translate it into German)
  - record and translate only the headings
  - represent the depth of the crawled websites with proper indentation (see example)
  - record the URLs of the crawled sites
  - highlight broken links
- find the links to other websites and recursively do the analysis for those websites (it is enough if you analyze the pages at a depth of 2 without visiting further links, you might also allow the user to configure this depth via the command line)
- store the results in a single markdown file (.md extension)

Note, also provide automated unit tests for each feature (we will not accept submissions without unit tests).

## Assignment 2:
In this assignment, you will extend and maintain your previous solution.

#### New must have features

- New feature to allow  **concurrent crawling (and optionally also translation)**  of multiple websites. Given two or more URLs as input to your program, your solution should concurrently gather the data for the websites and their referenced websites. In the end, you should combine the results for each website in a single report while retaining the original structure of the websites.
- Implement proper and clean Error Handling. Various errors might occur when crawling and/or translating a website. Protect your application against crashing by handling the errors and informing the user by logging the error in the report.
- The dependencies on third-party libraries, e.g., jsoup, should be small to allow future updates or even replacements of the third-party libraries.

## How to use the application:

This program provides a command-line interface (CLI) for crawling websites. It allows users to specify parameters such as the URL to crawl, the depth of crawling, target websites to crawl in depth, and the language the content crawled should be translated to.

### Prerequisites

- Java Development Kit (JDK) version 19 or later installed on your system.

### Installation

1. Clone or download this repository.
2. Compile the source code using a Java compiler.
3. (Optional) Register at https://www.deepl.com/translator to receive an api authorization key.
4. (Optional) Create an environment variable at your system called `DEEPL_AUTH` having the authorization key as value.

Note: Localization will be skipped if no deepl authorization key is provided.

### Running the Program

Navigate to the directory where the compiled `.class` files are located, and execute the following command:

```bash
java Main [options]
```

### Command-Line Options

-   `-u, --url <URL>`: Specify the URL to crawl (required).
-   `-d, --depth <depth>`: Set the depth of crawling (optional, default is 0).
-   `-w, --websites <websites>`: Specify websites to crawl in depth (optional, supports multiple values).
-   `-l, --language <language>`: Set the target language for crawling (optional, default is "en").

### Examples

1.  Basic usage:
    `java Main -u https://example.com`

2.  Specifying depth and language:
    `java Main -u https://example.com -d 3 -l en`

3.  Specifying websites to crawl in depth
    `java Main -u https://example.com -d 1 -w https://example2.com https://example3.com`

