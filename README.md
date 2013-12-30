<h1>Java S3 All or Nothing</h1>

<p>Java S3 All or Nothing is a small one-evening project to create a simple Java program to make operations will all the contents in a AWS S3 bucket.</p>

<p>The purpose was being able to download the entire bucket to a folder and upload a folder to a new bucket.</p>

<p>I upload here this code to allow developers to learn from it, or improve it if they want.</p>

<p>There are four classes:</p>

<ul>
   <li>S3Manager handles the client</li>
   <li>BucketDownloader has the code to download all the contents of a bucket to a folder</li>
   <li>BucketUploader has the code to upload all contents of a folder to a bucket, preserving the folder structure in the key names</li>
   <li>BucketDeleter implements the function of deleting a bucket and all its contents automatically</li>
</ul>
<p>The lib/ folder contains all libraries needed by this program, which consist in the AWS SDK and other libraries that it requires.</p>

 <p>Have a nice day!</p>
 <p>Guillermo</p>

