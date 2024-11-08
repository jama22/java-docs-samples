/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package compute.disks;

// [START compute_disk_start_replication]
// Uncomment this line if your disk has zonal location.
// import com.google.cloud.compute.v1.DisksClient;
// import com.google.cloud.compute.v1.DisksStartAsyncReplicationRequest;
import com.google.cloud.compute.v1.Operation;
import com.google.cloud.compute.v1.RegionDisksClient;
import com.google.cloud.compute.v1.RegionDisksStartAsyncReplicationRequest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StartDiskReplication {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    // TODO(developer): Replace these variables before running the sample.
    // The project that contains the primary disk.
    String primaryProjectId = "PRIMARY_PROJECT_ID";
    // Name of the primary disk.
    String primaryDiskName = "PRIMARY_DISK_NAME";
    // Name of the region in which your primary disk is located.
    // Learn more about zones and regions:
    // https://cloud.google.com/compute/docs/disks/async-pd/about#supported_region_pairs
    String primaryDiskLocation = "YOUR_PRIMARY_DISK_LOCATION";
    // Path to your secondary disk.
    // This value uses the following format for zonal location:
    // "projects/%sYOUR_PROJECT_ID/zones/YOUR_PRIMARY_DISK_LOCATION/disks/PRIMARY_DISK_NAME"
    String secondaryDiskPath =
        "projects/%s/regions/YOUR_SECONDARY_DISK_LOCATION/disks/SECONDARY_DISK_NAME";

    startDiskAsyncReplication(
        primaryProjectId, primaryDiskName, primaryDiskLocation, secondaryDiskPath);
  }

  // Starts asynchronous replication for the specified disk.
  public static void startDiskAsyncReplication(String primaryProjectId, String primaryDiskName,
      String primaryDiskLocation, String secondaryDiskPath)
      throws IOException, ExecutionException, InterruptedException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.

    // Uncomment these lines if your disk has zonal location.
    // try (DisksClient disksClient = DisksClient.create()) {
    // DisksStartAsyncReplicationRequest  startAsyncReplicationRequest =
    //    DisksStartAsyncReplicationRequest.newBuilder()
    //        .setAsyncSecondaryDisk(secondaryDiskPath)
    //       .build();

    try (RegionDisksClient disksClient = RegionDisksClient.create()) {

      RegionDisksStartAsyncReplicationRequest replicationRequest =
          RegionDisksStartAsyncReplicationRequest.newBuilder()
              .setAsyncSecondaryDisk(secondaryDiskPath)
              .build();

      Operation response = disksClient.startAsyncReplicationAsync(
          primaryProjectId, primaryDiskLocation, primaryDiskName, replicationRequest).get();

      if (response.hasError()) {
        return;
      }
      System.out.println("Async replication started successfully.");
    }
  }
}
// [END compute_disk_start_replication]