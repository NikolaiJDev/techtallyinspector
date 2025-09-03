import React from 'react'

const SettingsPage: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Settings</h1>
      </div>

      <div className="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
        <p className="text-gray-600">
          System settings and configuration will be implemented here.
          This will include application settings, integrations, and system maintenance options.
        </p>
      </div>
    </div>
  )
}

export default SettingsPage